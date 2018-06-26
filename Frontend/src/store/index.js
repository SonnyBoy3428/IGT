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
    order_history: [],
    full_order: undefined
}

const getters = {
    items() {
        return state.Items;
    }    
}

const mutations = {
    addPseudoQuantity(state) {
        state.Items = [];
        axios.get('http://localhost:3000/items').then(items => {
        // axios.get(HOST + '/itemService/getAllItems').then(function(items) {
            items.data.Items.forEach(s => {
                s.ItemQuantity = 0;
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
    sendOrder(state, [items, customerId]) {
        console.log(items, customerId);
        axios.post(`${HOST}/orderAndOrderlineService/createCompleteOrderForCustomerId=${customerId}`, items).then(order => state.order_history = [...order.data.Order, ...state.order_history])
    },
    createUser(state, user) {
        axios.post(HOST + '/customerService/createCustomer', user).then(user => state.user = user.data.Customer);
    },
    alterUser(state, user) {
        axios.post(HOST + '/customerService/updateCustomer', user).then(user => state.user = user.data.Customer);
    },
    deleteUser(state, userId) {
        axios.delete(`${HOST}/customerService/deleteCustomerByCustomerId=${userId}`).then(user => { 
            state.user = undefined;
        });
    },
    getUser(state, userId) {
        // axios.get(`${HOST}/customerService/getAllCustomerOrdersByCustomerId=${userId}`).then(obj => {
        axios.get('http://localhost:3000/allCustomerOrders').then(obj => {
            state.user = obj.data.Customer;
            state.order_history = obj.data.Orders;
        });
    },
    getFullOrder(state, orderId) {
        // axios.get(`${HOST}/orderAndOrderlineService/getCompleteOrderById=${orderId}`).then(obj => {
        axios.get('http://localhost:3000/completeOrder').then(obj => {
            state.full_order = obj.data.ItemsAndQuantities;
        });

    }
}

export default new Vuex.Store({
    state, 
    getters,
    // actions,
    mutations
})
