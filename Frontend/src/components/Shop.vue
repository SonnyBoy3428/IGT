<template>
  <div class="container-fluid shop">
    <header>
      <span>
        IGT Shop
        </span>
      <span style="float:right">
  
        <a href="#" v-if="customerId==undefined" @click="toggleModal('login')">Login</a>
        <span v-else>Hello, {{ user.FirstName }} {{ user.LastName }}
          <a href="#" @click="toggleModal('update')">Edit Profile</a>
        </span>
      </span>
    </header>
    <div class="row mb80">
      <div class="item col-sm-3" v-for="item in items" :key="item.ItemId" v-if="item.ItemId">
        <div class="card-body">
          <h5 class="card-title">{{ item.ItemName }}</h5>
          <p class="card-text">Price: {{ item.Price }} €</p>
          <a href="#" v-if="item.ItemQuantity > 0" class="btn btn-primary" @click.prevent="decreaseQuantity(item.ItemId)">-</a>
          <a href="#" v-else class="btn btn-secondary">-</a>
          <span>Quantity: {{ item.ItemQuantity }}</span>
          <a href="#" class="btn btn-primary" @click.prevent="increaseQuantity(item.ItemId)">+</a>
        </div>
      </div>
    </div>
    <div><button class="btn btn-primary" @click="sendOrder()">Order now</button></div>
    <div v-if="order_history.length > 0">
      <!-- <h3>Recent Orders:</h3> -->
      <table class="table">
        <thead><td colspan="6">Recent Orders</td></thead>
        <tbody>
        <tr>
          <td>Order ID</td>
          <td>Customer ID</td>
          <td>Order Date</td>
          <td>Total Cost</td>
          <td>Order Carried Out?</td>
          <td></td>
        </tr>
        <tr v-for="order in order_history" :key="order.OrderId">
          <td>{{ order.OrderId }}</td>
          <td>{{ order.CustomerId }}</td>
          <td>{{ order.OrderDate }}</td>
          <td>{{ order.TotalCost }}</td>
          <td>{{ order.OrderCarriedOut }}</td>
          <td>
            <a href="#" @click="getFullOrder(order.OrderId)">Show Full Order</a>
          </td>
        </tr>
        </tbody>
      </table>
      <!-- <div v-for="order in order_history" :key="order.OrderId">
        <span>Order ID {{ order.OrderId }}</span>
        <span>Customer ID {{ order.CustomerId }}</span>
        <span>Order Date {{ order.OrderDate }}</span>
        <span>Total Cost {{ order.TotalCost }}</span>
        <span>Order Carried Out {{ order.OrderCarriedOut }}</span>
      </div> -->
    </div>
    <b-modal v-model="show" :ok-only="true" ok-title="Close">
      <b-container fluid>
        <b-row>
          <b-col v-if="modalType == 'login'">
            <span>
                Login
              </span>
            <b-form-input id="customerID" placeholder="customer ID" v-model="customerId">
            </b-form-input>
            <b-button @click="toggleModal('login')">Login</b-button>
          </b-col>
          <b-col>
            <span v-if="modalType == 'login'">
                Sign up
              </span>
            <span v-else>
                Edit Profile
              </span>
            <b-form-group>
              <b-form-input placeholder="FirstName" id="FirstName" v-model="newCustomer.FirstName"></b-form-input>
              <b-form-input placeholder="LastName" id="LastName" v-model="newCustomer.LastName"></b-form-input>
              <b-form-input placeholder="Address" id="Address" v-model="newCustomer.Address"></b-form-input>
              <b-form-input placeholder="Telephone" id="Telephone" v-model="newCustomer.Telephone"></b-form-input>
              <b-form-input placeholder="CreditCardNr" id="CreditCardNr" v-model="newCustomer.CreditCardNr"></b-form-input>
              <b-form-input placeholder="District ID" id="DistrictId" v-model="newCustomer.DistrictId"></b-form-input>
              <b-button @click="createUser()" v-if="modalType == 'login'">Sign up</b-button>
              <b-button-group v-else>
                <b-button @click="alterUser()">
                  Update Profile
                </b-button>
                <b-button variant="danger" @click="deleteUser()">
                  Delete Profile
                </b-button>
  
              </b-button-group>
            </b-form-group>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>

    <b-modal v-model="showFullOrder" :ok-only="true" ok-title="Close">
      <b-container fluid>
        <table class="table">
          <tr>
            <td>Item ID</td>
            <td>Item Name</td>
            <td>Price</td>
            <td>Quantity</td>
          </tr>
          <tr v-for="item in full_order" :key="item.ItemId">
            <td>{{ item.ItemId }}</td>
            <td>{{ item.ItemName }}</td>
            <td>{{ item.Price }}</td>
            <td>{{ item.ItemQuantity }}</td>
          </tr>
        </table>
        <!-- <div v-for="item in full_order" :key="item.ItemId">
          {{ item }}
        </div> -->
      </b-container>
    </b-modal>
  </div>
</template>

<script>
  export default {
    name: 'Shop',
    data() {
      return {
        show: false,
        showFullOrder: false,
        customerId: undefined,
        newCustomer: {
          FirstName: undefined,
          LastName: undefined,
          Address: undefined,
          Telephone: undefined,
          CreditCardNr: undefined,
          DistrictId: undefined
        },
        modalType: ''
      }
    },
    computed: {
      items: function() {
        return this.$store.state.Items;
      },
      user: function() {
        return this.$store.state.user;
      },
      order_history: function() {
        return this.$store.state.order_history;
      },
      full_order: function() {
        return this.$store.state.full_order;
      }
    },
    methods: {
      increaseQuantity(id) {
        this.$store.commit('increaseQuantity', id);
      },
      decreaseQuantity(id) {
        this.$store.commit('decreaseQuantity', id);
      },
      sendOrder() {
        const custId = this.customerId;
        if (!this.customerId) return this.toggleModal('login');
  
        let items = this.$store.state.Items.filter(s => s.ItemId && s.ItemQuantity > 0);
        // console.log(items, custId);
        if (items.length > 0) this.$store.commit('sendOrder', [{ ItemsAndQuantities: items }, custId]);
      },
      toggleModal(s) {
        this.show = !this.show;
        s == 'login' ? this.modalType = 'login' : s == 'update' ? this.modalType = 'update' : this.modalType = '';
        if (!!this.customerId) this.$store.commit('getUser', this.customerId)
      },
      createUser() {
        this.$store.commit('createUser', this.newCustomer);
        this.customerId = this.user.CustomerId;
      },
      alterUser() {
        this.$store.commit('alterUser', this.newCustomer);
      },
      deleteUser() {
        this.$store.commit('deleteUser', this.customerId);
      },
      getFullOrder(orderId) {
        this.$store.commit('getFullOrder', orderId);
        this.showFullOrder = true;
      }
    },
    mounted() {
      this.$store.commit('addPseudoQuantity');
    }
  };
</script>

<style scoped>
  .mb80 {
    margin-bottom: 80px;
  }
  
  .item {
    min-width: 100 px;
    min-height: 100 px;
  }
  
  .card-body {
    border: 2px solid lightgrey;
    border-radius: 5px;
  }
  
  input {
    margin: 10px auto;
  }
  
  header {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    padding: 20px;
  }

  .table {
    margin-top: 30px;
  }
</style>
