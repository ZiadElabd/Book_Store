<template>

<div class="products">
       <Navbar />
    <div v-if="isAdmin" class="add">
       <router-link to="/newProduct" > <span>+</span></router-link>
     
    </div>
  <div class="container">
    <div class="searchContent">
     <div class="search">
          <form class="form-inline my-2 my-lg-0 ">
            <input class="form-control mr-sm-2" 
            type="search"
             v-model="searchValue"
              @keyup="filter"
              placeholder="Search using Product name "
               aria-label="Search">
              
          </form>
          
      </div>
      <div class="searchBtn">
        <select id="cars" v-model="searchType" @change="onChange($event)">
          <option value="title">Title</option>
          <option value="ISBN">ISBN</option>
          <option value="author">Author</option>
          <option value="Publisher">Publisher</option>
        </select>
        <b-button variant="primary" @click="search" class="btn  btn-lg btn-full "> Search </b-button>
      </div>

    </div>
        <div class="row">
                <div class="col-sm-3" v-for="product in products" :key="product.productId">
                    <div class="box-container"> 
                    <div class="box-img"> 
                        <img :src="product.image" />
                        <div class="box-price">${{product.price}}</div>
                    </div>
                    <h4 class="box-title">{{product.title}}</h4>  
                    <div class="box-heading text-uppercase">{{product.categoryName}}</div>
                    <div class="box-btns">
                        <a @click="view(product)" class="btn btn-primary text-uppercase">view</a>
                    </div>
                    <div class="box-id">{{product.ISBN}}</div>
                    </div>
                </div> 
               
        </div>
  </div>




</div>
</template>

<script>
import Navbar from "../components/nbar.vue";
import { bus } from '../main'
export default {
  name: "Products",
  components: {
    Navbar,
  },
  data() {
    return{
        searchValue:'',
        searchType: 'ISBN',
        products:[],
        category:'Science'
    }
  },
  computed: {
    //  filteredList() {
    //   return this.products.filter(p => {
    //     return p.name.toLowerCase().includes(this.searchValue.toLowerCase())
    //   })
    // },
    isAdmin(){
      return this.$store.state.role;
    },
    userID(){
       return this.$store.state.userID;
    }
  },
  methods: {
      onChange(e) {
              console.log(e.target.value);
      },
    filter(){
       this.filtered = this.filteredList ;
       console.log(this.filtered);
       console.log(this.filteredList);
    },
    parseJSON: function (resp) {
        return resp.json();
    },
    checkStatus: function (resp) {
        console.log('status');
        console.log(resp);
        if (resp.status >= 200 && resp.status < 300) {
            console.log('good status');
            return resp;
        }
        console.log('bad status');
        return this.parseJSON(resp).then((resp) => {
            throw resp;
        });
    },
    async search(){
        try {
          let response = await fetch( "http://localhost:8080/admin/search/" + this.userID, {
              method: "get",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({
                categoryName: this.category,
                type:this.searchType,
                searchText:this.searchValue
              }) 
          }).then(this.checkStatus)
          .then(this.parseJSON);
          console.log(response);
          this.products = response;
      } catch (error) {
          alert('error');
      }
    },
    view(product){
      if(this.isAdmin){
        this.$router.push({ name: "newProduct" , params: { product: product } });
      }else{
        this.$router.push({ name: "displayProduct" , params: { product: product } });
      }
    },
    async getProducts(category){
      this.products = [];
      console.log(category + 'in the products components');
      try {
          let response = await fetch( "http://localhost:8080/admin/getProducts/" + category + '/' + this.userID , {
              method: "get", 
          }).then(this.checkStatus)
          .then(this.parseJSON);
          console.log(response);
          this.products = response;
      } catch (error) {
          alert('error');
      }
    }
  },
  created() {
    console.log("created");
    bus.$on('changeCategory', (data) => {
      this.getProducts(data);
      this.category = data;
    })
    this.getProducts('Science');
  },
  
};
</script>
<style scoped>
.container{
  width: 80%;
}
.box-container {
  text-align: center;
  width: 100%;
  border: 1px solid #999;
  margin: 25px 0;
  margin-top: 50px;
  padding: 6px 15px 0;
}
.row{
    margin-top: 80px;
}
.box-container .box-id {
  background-color: #eee;
  display: block;
  padding: 6px 15px;
  margin-top: 10px;
}
.box-container .box-img {
  margin-top: -35px;
  position: relative;
}
.box-container .box-price {
  display: inline-block;
  background-color: #3498db;
  border-radius: 0px;
  padding: 4px 10px;
  margin: 15px auto 0;
  color: #fff;
  position: absolute;
  top: 0;
  left: 0;
}
.box-container .box-title {
  font-size: 14px;
  text-transform: uppercase;
  font-weight: bold;
}
.box-container .box-heading {
  margin: 10px 0;
}
.box-container .btn {
  font-size: 13px;
}
.box-container img {
  display: block;
  max-width: 100%;
}
.add{
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: #3498db;
  color: #FFF;
  position: fixed;
  right: 50px;
  bottom: 50px;
  z-index: 10;
  text-align: center;
  font: 900;
  font-size: 40px;
  cursor: pointer;
}
.add span{
  display: block;
  position: absolute;
  top: -5px;
  left: 15px;
}
a{
  text-decoration: none;
  color: #FFF;
}
.search{
  margin-top: 80px;
  width: 100%;
}
.searchContent{
  display: flex;
}
.searchBtn{
  width: 400px;
  margin-top: 79px;
  margin-left: 20px;
  display: flex;
}
.btn{
  line-height: 1.3;
}
select{
  width: 180px;
  margin-right: 20px;
  border: 1px solid #999 ;
  border-radius: 5px;
  color: #959393;
  outline: none;
}

</style>