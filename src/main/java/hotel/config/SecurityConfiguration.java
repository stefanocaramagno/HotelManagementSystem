package hotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import hotel.models.CleaningStaff;
import hotel.models.Customer;
import hotel.repositories.CustomerRepository;
import hotel.repositories.CleaningStaffRepository;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SecurityConfiguration {

    // Attributi
    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final CleaningStaffRepository cleaningStaffRepository;

    // Costruttore
    public SecurityConfiguration(CustomerRepository customerRepository, CleaningStaffRepository cleaningStaffRepository) {
        this.customerRepository = customerRepository;
        this.cleaningStaffRepository = cleaningStaffRepository;
    }

    // Bean per la Codifica delle Password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configurazione della Catena di Filtri di Sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            
                // Aggiungi una Regola Esplicita per la Home
                .requestMatchers("/").permitAll()
                .requestMatchers("/home").permitAll()
                
                // Regole di Autorizzazione per i Ruoli
                .requestMatchers("/customer/customerAccess").permitAll()
                .requestMatchers("/api/customer/register").permitAll()
                .requestMatchers("/customer/**").hasRole("CUSTOMER")
    
                .requestMatchers("/receptionist/receptionistAccess").permitAll()
                .requestMatchers("/api/receptionist/register").permitAll()
                .requestMatchers("/receptionist/**").hasRole("RECEPTIONIST")
    
                .requestMatchers("/operationsManager/operationsManagerAccess").permitAll()
                .requestMatchers("/api/operationsManager/register").permitAll()
                .requestMatchers("/operationsManager/**").hasRole("OPERATIONS_MANAGER")
    
                .requestMatchers("/cleaningStaff/cleaningStaffAccess").permitAll()
                .requestMatchers("/api/cleaningStaff/register").permitAll()
                .requestMatchers("/cleaningStaff/**").hasRole("CLEANING_STAFF")

                // Accesso Pubblico alle Risorse Statiche
                .requestMatchers("/static/**", "/app/**", "/dist/**", "/asset/**", "/component/**").permitAll()
    
                // Richiedi Autenticazione per tutte le altre Richieste
                .anyRequest().authenticated()
            )
    
            // Disabilita CSRF per richieste API REST
            .csrf(csrf -> csrf.disable()) 

            // Configura Login unico con Success Handler personalizzato
            .formLogin(form -> form
                .loginPage("/home")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/home?error=true")
                .permitAll()
            )

            // Configurazione per il Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Configurazione per la Gestione delle Sessioni
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
    
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = "/home";
    
            String role = authentication.getAuthorities().iterator().next().getAuthority();
    
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    
            final String[] fullName = {"Guest"}; 
    
            if (user.getUsername() != null) {
                Customer customer = customerRepository.findByEmail(user.getUsername());
                if (customer != null) {
                    fullName[0] = customer.getFullName();
                }

                CleaningStaff cleaningStaff = cleaningStaffRepository.findByEmail(user.getUsername());
                if (cleaningStaff != null) {
                    fullName[0] = cleaningStaff.getFullName();
                }
            }
    
            WebAuthenticationDetails details = new WebAuthenticationDetails(request) {
                @Override
                public String toString() {
                    return fullName[0];
                }
            };
    
            Authentication updatedAuth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    authentication.getAuthorities()
            );
            ((UsernamePasswordAuthenticationToken) updatedAuth).setDetails(details);
    
            SecurityContextHolder.getContext().setAuthentication(updatedAuth);
    
            switch (role) {
                case "ROLE_CUSTOMER":
                    redirectUrl = "/customer/customerArea";
                    break;
                case "ROLE_RECEPTIONIST":
                    redirectUrl = "/receptionist/receptionistArea";
                    break;
                case "ROLE_OPERATIONS_MANAGER":
                    redirectUrl = "/operationsManager/operationsManagerArea";
                    break;
                case "ROLE_CLEANING_STAFF":
                    redirectUrl = "/cleaningStaff/cleaningStaffArea";
                    break;
                default:
                    redirectUrl = "/home";
            }
    
            response.sendRedirect(redirectUrl);
        };
    }

}