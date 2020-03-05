import axios from 'axios'

const API_URL = 'http://localhost:8080'
const FULL_API_URL = `${API_URL}/api`

class EquipmentDataService {

    retrieveAllEquipments() {
        return axios.get(`${FULL_API_URL}/equipment`,
            //{ headers: { authorization: 'Basic ' + window.btoa(INSTRUCTOR + ":" + PASSWORD) } }
        );
    }

    deleteEquipment(equipment) {
        return axios.delete(`${FULL_API_URL}/equipment/${equipment.id}`);
    }
}

export default new EquipmentDataService()
