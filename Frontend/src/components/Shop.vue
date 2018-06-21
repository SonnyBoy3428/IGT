<template>
  <div class="container-fluid shop">
    <div class="row mb80">
      <div class="item col-sm-3" v-for="item in items" :key="item.ItemId" v-if="item.ItemId">
        <div class="card-body">
          <h5 class="card-title">{{ item.ItemName }}</h5>
          <p class="card-text">Preis: {{ item.Price }} €</p>
          <a href="#" v-if="item.quantity > 0" class="btn btn-primary" @click.prevent="decreaseQuantity(item.ItemId)">-</a>
          <a href="#" v-else class="btn btn-secondary">-</a>
          <span>Stückzahl: {{ item.quantity }}</span>
          <a href="#" class="btn btn-primary" @click.prevent="increaseQuantity(item.ItemId)">+</a>
        </div>
      </div>
    </div>
    <div><button class="btn btn-primary" @click="sendOrder()">Jetzt bestellen</button></div>
    <b-modal v-model="show" :ok-only="true" ok-title="Close">
      <b-container fluid>
        <b-row>
          <b-col>
            <span>
                Login
              </span>
            <b-form-input id="customerID" placeholder="customer ID" v-model="customerId">
            </b-form-input>
            <b-button @click="toggleModal()">Login</b-button>
          </b-col>
          <b-col>
            <span>
                Sign up
              </span>
            <b-form-group>
              <b-form-input placeholder="Vorname" id="FirstName" v-model="newCustomer.FirstName"></b-form-input>
              <b-form-input placeholder="Nachname" id="LastName" v-model="newCustomer.LastName"></b-form-input>
              <b-form-input placeholder="Adresse" id="Address" v-model="newCustomer.Address"></b-form-input>
              <b-form-input placeholder="Telefon" id="Telephone" v-model="newCustomer.Telephone"></b-form-input>
              <b-form-input placeholder="Kreditkartennummer" id="CreditCardNr" v-model="newCustomer.CreditCardNr"></b-form-input>
              <b-form-input placeholder="District ID" id="DistrictId" v-model="newCustomer.DistrictId"></b-form-input>
              <b-button @click="createUser()">Sign up</b-button>
            </b-form-group>
          </b-col>
        </b-row>
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
        customerId: undefined,
        newCustomer: {
          FirstName: undefined,
          LastName: undefined,
          Address: undefined,
          Telephone: undefined,
          CreditCardNr: undefined,
          DistrictId: undefined
        }
      }
    },
    computed: {
      items: function() {
        return this.$store.state.Items;
      },
    },
    methods: {
      increaseQuantity(id) {
        this.$store.commit('increaseQuantity', id);
      },
      decreaseQuantity(id) {
        this.$store.commit('decreaseQuantity', id);
      },
      sendOrder() {
        if (!this.customerId) return this.toggleModal();
  
        let items = this.$store.state.Items.filter(s => s.ItemId && s.quantity > 0);
        console.log(items, this.customerId);
        this.$store.commit('sendOrder', items, this.customerId);
      },
      toggleModal() {
        this.show = !this.show;
      },
      createUser() {
        this.$store.commit('createUser', this.newCustomer)
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
</style>
