import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

const state = {
    hello: 'hello world',
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
    ]
}

const getters = {
    items() {
        return state.Items;
    }    
}

const mutations = {
    addPseudoQuantity(state) {
        state.Items.forEach(s => s.quantity = 0);
        state.Items.push({});
    },
    increaseQuantity(state, id) {
        state.Items.find(function(e) {
            return e.ItemId == id;
        }).quantity++;
        state.Items.push({});

    },
    decreaseQuantity(state, id) {
        state.Items.find(function(e) {
            return e.ItemId == id;
        }).quantity--;
        state.Items.push({});
    }
}

export default new Vuex.Store({
    state, 
    getters,
    // actions,
    mutations
})