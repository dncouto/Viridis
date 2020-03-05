import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080'
const FULL_API_URL = `${API_BASE_URL}/api/equipment`

class EquipmentDataService {

    retrieveAllEquipments() {
        return axios.get(`${FULL_API_URL}`);
    }

    createEquipment(equipment) {
        return axios.post(`${FULL_API_URL}`, equipment);
    }

    updateEquipment(equipment) {
        return axios.put(`${FULL_API_URL}`, equipment);
    }

    deleteEquipment(equipment) {
        return axios.delete(`${FULL_API_URL}/${equipment.id}`);
    }
}

export default new EquipmentDataService()
