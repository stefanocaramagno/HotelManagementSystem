// Startpoint per Ottenere la Lista delle Richieste di Modifica delle Prenotazioni confermate da un Pagamento Precedente
async function getModificationRequests() {
    try {
      const response = await fetch('/api/hotelManagementSystem/getModificationRequests');
      if (response.ok) {
        const requests = await response.json();
        const container = document.getElementById('modification-list');
        container.innerHTML = '';
  
        requests.forEach((request) => {
          const requestDiv = document.createElement('div');
          requestDiv.classList.add(
            'bg-white',
            'rounded-lg',
            'shadow-lg',
            'p-6',
            'hover:shadow-xl',
            'transition-shadow'
          );
  
          requestDiv.innerHTML = `
            <h2 class="text-2xl font-bold text-gray-800 mb-4">Booking #${request.idBooking}</h2>
            <div class="text-gray-600 mb-4">
              <div class="flex items-center bg-blue-100 p-4 rounded-lg mb-4">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-blue-500 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5.121 17.804A5.002 5.002 0 0110 15h4a5.002 5.002 0 014.879 2.804M7 7a3 3 0 016 0m5 11H2" />
                </svg>
                <p class="text-lg font-bold text-blue-800"> ${request.customerName}</p>
              </div>
              <div class="flex items-center bg-yellow-100 p-4 rounded-lg mb-4">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-yellow-500 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2m0 0l2-2m-2 2l-2-2m2 2V4m0 16H4m16 0h-8" />
                </svg>
                <p class="text-lg font-bold text-yellow-800">Status: ${request.status}</p>
              </div>
              <p><strong>Room Type:</strong> ${request.currentRoomType}</p>
              <p><strong>Current Start Date:</strong> ${request.currentStartDate}</p>
              <p><strong>Current End Date:</strong> ${request.currentEndDate}</p>
            </div>
            <div class="bg-gray-100 p-4 rounded-lg mb-4">
              <h3 class="text-lg font-semibold text-gray-800 mb-2">Requested Changes:</h3>
              <ul class="text-gray-600">
                <li><strong>New Start Date:</strong> ${request.newStartDate}</li>
                <li><strong>New End Date:</strong> ${request.newEndDate}</li>
              </ul>
            </div>
            <div class="mt-6 flex justify-end space-x-4">
              <button onclick="manageRequestModifyBooking(${request.idBooking}, true, '${request.currentStartDate}', '${request.currentEndDate}')" 
                class="bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 transition">
                Approve
              </button>
              <button onclick="manageRequestModifyBooking(${request.idBooking}, false, '${request.currentStartDate}', '${request.currentEndDate}')" 
                class="bg-red-600 text-white py-2 px-4 rounded-md hover:bg-red-700 transition">
                Reject
              </button>
            </div>
          `;
          container.appendChild(requestDiv);
        });
      } else {
        console.error('Failed to fetch modification requests.');
      }
    } catch (error) {
      console.error('Error fetching modification requests:', error);
    }
  }
  
// Startpoint per Gestire una Richiesta di Modifica di una Prenotazione confermata da un Pagamento Precedente
async function manageRequestModifyBooking(idBooking, approved, originalStartDate, originalEndDate) {
    try {
        const response = await fetch('/api/hotelManagementSystem/manageRequestModifyBooking', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ idBooking, approved, originalStartDate, originalEndDate }),
        });

        if (response.ok) {
            alert('Modification request reviewed successfully!');
            getModificationRequests();
        } else {
            const errorMessage = await response.text();
            alert('Error: ' + errorMessage);
        }
    } catch (error) {
        console.error('Error reviewing modification request:', error);
        alert('An unexpected error occurred.');
    }
}

async function manageRequestModifyBooking(idBooking, approved, originalStartDate, originalEndDate) {
  try {
      const queryStringParameters = new URLSearchParams({
        idBooking: idBooking,
        approved: approved,
        originalStartDate: originalStartDate,
        originalEndDate: originalEndDate
      }).toString();

      const url = `/api/hotelSystemManagement/manageRequestModifyBooking?${queryStringParameters}`;

      const response = await fetch(url, { method: 'POST' });

      if (response.ok) {
          alert('Modification request reviewed successfully!');
          getModificationRequests();
      } else {
          const errorMessage = await response.text();
          alert('Error: ' + errorMessage);
      }
  } catch (error) {
      console.error('Error reviewing modification request:', error);
      alert('An unexpected error occurred.');
  }
}

// Configurazione di Tutti i Listener al Caricamento della Pagina
document.addEventListener('DOMContentLoaded', getModificationRequests);
  