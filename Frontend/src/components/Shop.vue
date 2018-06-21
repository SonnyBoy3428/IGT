<template>
  <div class="container-fluid shop">
    <!-- <h1 class="mb80">Shop</h1> -->
    <div class="row mb80">
      <div class="item col-sm-3" v-for="item in items" :key="item.ItemId" v-if="item.ItemId">
        <!-- <img class="card-img-top" src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22286%22%20height%3D%22180%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20286%20180%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_1641e081fcc%20text%20%7B%20fill%3Argba(255%2C255%2C255%2C.75)%3Bfont-weight%3Anormal%3Bfont-family%3AHelvetica%2C%20monospace%3Bfont-size%3A14pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_1641e081fcc%22%3E%3Crect%20width%3D%22286%22%20height%3D%22180%22%20fill%3D%22%23777%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22106.3984375%22%20y%3D%2296.3%22%3E286x180%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E" -->
        <!-- alt="Card image cap"> -->
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
    <div><button class="btn btn-primary" @click="showModal()">Jetzt bestellen</button></div>
    <b-modal v-model="show" @ok="sendOrder()">
      Customer ID:
      <b-form-input type="text" v-model="customerId"></b-form-input>
  
    </b-modal>
  </div>
</template>

<script>
  export default {
    name: 'Shop',
    data() {
      return {
        show: false,
        customerId: undefined
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
        let items = this.$store.state.Items.filter(s => s.ItemId && s.quantity > 0);
        this.$store.commit('sendOrder', items, this.customerId);
      },
      showModal() {
        this.show = true;
      },
    },
    mounted() {
      this.$store.commit('addPseudoQuantity');
      fetch('https://jsonplaceholder.typicode.com/posts/1')
        .then(response => response.json())
        .then(json => console.log(json))
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
</style>
