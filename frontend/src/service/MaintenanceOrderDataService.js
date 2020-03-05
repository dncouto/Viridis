import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080'
const FULL_API_URL = `${API_BASE_URL}/api/maintenance-order`

class MaintenanceOrderDataService {

    retrieveAllOrders() {
        return axios.get(`${FULL_API_URL}`);
    }

    createOrder(order) {
        return axios.post(`${FULL_API_URL}`, order);
    }

    updateOrder(order) {
        return axios.put(`${FULL_API_URL}`, order);
    }

    deleteOrder(order) {
        return axios.delete(`${FULL_API_URL}/${order.id}`);
    }
}

export default new MaintenanceOrderDataService()
