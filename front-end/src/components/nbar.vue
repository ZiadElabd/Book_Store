<template>
  <nav class="navbar navbar-dark bg-primary navbar navbar-expand-lg">
     <div class="container">
       <ul class="categories navbar-nav  mr-auto">
          <li @click="getProductsEvent('Clothing')" class="category_item">
            Clothing
          </li>
          <li @click="getProductsEvent('Electronics')" class="category_item">
            Electronics
          </li>
          <li @click="getProductsEvent('Shoes')" class="category_item">
            Shoes
          </li>
          <li @click="getProductsEvent('Watches')" class="category_item">
            Watches
          </li>
          <li @click="getProductsEvent('Jewallery')" class="category_item">
            Jewallery
          </li>
          <li @click="getProductsEvent('Sports')" class="category_item">
            Sports
          </li>
          
        </ul>

      <div class="setting">
          <div v-if="!isAdmin" class="icon">
            <i class="fa fa-shopping-cart" aria-hidden="true"></i>
          </div>
          <b-dropdown>
            <template #button-content>
                  <em><font-awesome-icon icon="cog" /></em>
            </template>
            <b-dropdown-item to="/Settings">Settings</b-dropdown-item>
                <b-dropdown-item to="/">Log Out</b-dropdown-item>
          </b-dropdown>
      </div>
     </div>

  </nav>
</template>
<script>
import { bus } from '../main'
export default {
  name: "Navbar",
  computed: {
    isAdmin(){
      return this.$store.state.role;
    }
  },
  methods:{
    getProductsEvent(category){
      console.log(category + ' in nav bar');
      this.$router.push({ name: "Products" }).catch(() => {});
      bus.$emit('changeCategory', category);
    }
  },
};
</script>

<style scoped>
.navbar {
  top: 0;
  width: 100%;
  position: fixed;
}
.nav li a {
  color: #898b8c;
}
.nav {
  font-size: 110%;
}

.navbar-brand a {
  color: #ee1144;
  font-weight: bolder;
  font-family: "Lobster", cursive;
  text-decoration: none;
}
.nav li a:hover,
.navbar-brand:hover {
  color: #ee1144;
}
.nav li a:hover {
  font-size: 97.5%;
}

div > .navbar-brand {
  font-family: "Lobster", cursive;
}
.nav-link a:focus {
  color: #ee1144;
  font-size: 97.5%;
}
.container {
  width: 80%;
}
.categories li{
  display: block;
  padding: 0 10px;
  color: #FFF;
  cursor: pointer;
  margin: 0 10px;
}




.btn-check:active+.btn-secondary, .btn-check:checked+.btn-secondary, .btn-secondary.active, .btn-secondary:active, .show>.btn-secondary.dropdown-toggle {
    color: #fff;
    background-color: inherit;
    border: none;
    outline: none;
}
nav{
  display: flex;
  justify-content: space-between;
}

.setting{
  display: flex;
}
.icon{
  color: #FFF;
  cursor: pointer;
  padding:15px 12px;
  margin-right: 10px;
  border-radius: 2px;
}
.icon:hover{
  background-color: #5C636A;
}

.icon i{
  font-size: 130%;
}

</style>
