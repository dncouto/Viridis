import axios from 'axios'

const API_URL = 'http://localhost:8080'
const FULL_API_URL = `${API_URL}/api/equipment`

class EquipmentDataService {

    retrieveAllEquipments() {
        return axios.get(`${FULL_API_URL}`,
            //{ headers: { authorization: 'Basic ' + window.btoa(INSTRUCTOR + ":" + PASSWORD) } }
        );
    }

    updateEquipment(equipment) {
        return axios.put(`${FULL_API_URL}`, equipment);
    }

    deleteEquipment(equipment) {
        return axios.delete(`${FULL_API_URL}/${equipment.id}`);
    }
}

export default new EquipmentDataService()
