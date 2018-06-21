import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

const HOST = '';

const state = {
    user: undefined,
    Items: [
        {
            ItemId: 1,
            ItemName: "MusterMonitor",
            Price: 10.0,
        },
        {
            ItemId: 2,
            ItemName: "MusterMaus",
            Price: 20.0,
        },
    ],
    order_history: []
}

const getters = {
    items() {
        return state.Items;
    }    
}

const mutations = {
    addPseudoQuantity(state) {
        state.Items = [];
        axios.get(HOST + '/itemService/getAllItems').then(function(items) {
            items.Items.forEach(s => {
                s.quantity = 0;
                state.Items.push(s);
            });
        });

        // In case of the static data from above:

        // state.Items.forEach(s => s.quantity = 0);
        // state.Items.push({});
    },
    increaseQuantity(state, id) {
        state.Items.find(function(e) {
            return e.ItemId == id;
        }).ItemQuantity++;
        state.Items.push({});

    },
    decreaseQuantity(state, id) {
        state.Items.find(function(e) {
            return e.ItemId == id;
        }).ItemQuantity--;
        state.Items.push({});
    },
    sendOrder(state, items, customerId) {
        axios.post(`${HOST}/orderAndOrderlineService/createCompleteOrderForCustomerId=${customerId}`, items).then(order => state.order_history = [...order.Order, ...state.order_history])
    },
    createUser(state, user) {
        axios.post(HOST + '/customerService/createCustomer', user).then(user => state.user = user.Customer);
    },
    alterUser(state, user) {
        axios.post(HOST + '/customerService/updateCustomer', user).then(user => state.user = user.Customer);
    },
    deleteUser(state, userId) {
        axios.delete(`${HOST}/customerService/deleteCustomerByCustomerId=${userId}`).then(user => { 
            state.user = undefined;
        });
    },
    getUser(state, userId) {
        // axios.get(`${HOST}/customerService/getCustomerById=${userId}`).then(user => state.user = user.Customer);
        axios.get(`${HOST}/customerService/getAllCustomerOrdersByCustomerId=${userId}`).then(obj => {
            state.user = obj.Customer;
            state.order_history = obj.Orders;
        });
    }
}

export default new Vuex.Store({
    state, 
    getters,
    // actions,
    mutations
})
