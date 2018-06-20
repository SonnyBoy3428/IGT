<template>
  <div class="container-fluid shop">
    <h1>Shop</h1>
    <div class="row">
      <div class="item col-sm-3" v-for="item in items2" :key="item.name">
        <img class="card-img-top" src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22286%22%20height%3D%22180%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20286%20180%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_1641e081fcc%20text%20%7B%20fill%3Argba(255%2C255%2C255%2C.75)%3Bfont-weight%3Anormal%3Bfont-family%3AHelvetica%2C%20monospace%3Bfont-size%3A14pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_1641e081fcc%22%3E%3Crect%20width%3D%22286%22%20height%3D%22180%22%20fill%3D%22%23777%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22106.3984375%22%20y%3D%2296.3%22%3E286x180%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
          alt="Card image cap">
        <div class="card-body">
          <h5 class="card-title">{{ item.name }}</h5>
          <p class="card-text">Preis: {{ item.price }} €</p>
          <a href="#" class="btn btn-primary" @click.prevent="log({ name: item.name, price: item.price })">Jetzt kaufen</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Shop',
    data() {
      return {};
    },
    computed: {
      items2: function() {
        var arr = [];
        for (var i = 0; i < 50; i++) {
          arr.push({
            name: `Artikel ${i+1}`,
            price: ((Math.random() + 0.1) * 70).toFixed(2)
          });
        }
        return arr;
      }
    },
    methods: {
      log({
        name: name,
        price: price
      }) {
        console.log(name + ' kostet ' + price);
      }
    },
    mounted() {
      console.log(typeof axios);
    }
  };
</script>

<style scoped>
  h1 {
    margin-bottom: 80px;
  }
  
  .item {
    min-width: 100 px;
    min-height: 100 px;
  }
</style>
