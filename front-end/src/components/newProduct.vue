<template>
      <div>
          <Navbar />
        <div class="product">
            <div class="photo">
                <img :src="profileURL" alt="">
            </div>
            <div class="description">
                
                <form>
                  <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input
                      type="text"
                      class="form-control"
                      id="name"
                      v-model="product.name"
                    />
                  </div>
                  <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <input
                      type="text"
                      class="form-control"
                      id="description"
                      v-model="product.description"
                    />
                  </div>
                   <div class="mb-3">
                      <label  class="form-label">Category</label>
                     <select v-model="product.categoryName" class="form-select" aria-label="Default select example">
                        <option selected>Open this select menu</option>
                        <option value="Clothing">Clothing</option>
                        <option value="Electronics">Electronics</option>
                        <option value="Shoes">Shoes</option>
                        <option value="Watches">Watches</option>
                        <option value="Jewallery">Jewallery</option>
                        <option value="Sports">Sports</option>
                      </select>
                   </div>
                    <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input
                      type="text"
                      class="form-control"
                      id="price"
                      v-model="product.price"
                    />
                  </div>
                   <div class="mb-3">
                    <label for="quantity" class="form-label">Quantity</label>
                    <input
                      type="text"
                      class="form-control"
                      id="quantity"
                      v-model="product.quantity"
                    />
                  </div>
                </form>
                <div class="changeprofile ">
                      <input
                        type="file"
                        @change="onprofileselected"
                        style="display:none"
                        ref="fileinput"
                      />
                      <span @click="$refs.fileinput.click()">Product photo</span>
                </div>
                <div class="form-group ">
                 <b-button variant="primary" @click.prevent="save" class="btn  btn-lg btn-full "> Save </b-button>
                </div>
            </div>

            
        </div>
      </div>
</template>
<script>
import Navbar from "../components/nbar.vue";
export default {
     components: {
    Navbar,
  },
  data() {
    return {
      profileURL: "",
      imageSelected: false,
      product: {
        isbn:"",
        title:"",
        noOfCopies:"",
        categoryId:"",
        price:"",
        image:"",
        publicationYear:"",
        threshold:"",
        publisherId:""
      }
    };
  },
  computed: {
      isAdmin(){
        return this.$store.state.role;
      },
      productParam(){
        return this.$route.params.product;
      },
      userID(){
        return this.$store.state.userID;
      }
  },
  methods:{
    onprofileselected: function(event) {
      this.imageSelected = true;
      this.product.image = event.target.files[0];
      this.getImageBase64(this.product.image);
    },
    getImageBase64: function(file) {
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.profileURL = this.product.image = reader.result;
      };
      reader.error = () => {
        alert("Error !!!");
      };
    },
    save(){
      if(this.productParam){
        this.updateProduct();
      } else{
        this.addProduct();
      }
      this.$router.push({ name: "Products"});
    },
    updateProduct(){
      console.log("updating product");
      fetch(
        "http://localhost:8080/admin/updateProduct/" +
          this.product.productId + '/' + 
          this.userID,
        {
          method: "put",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.product)
        }
      );
    },
    addProduct() {
      console.log("adding product");
      fetch(
        "http://localhost:8080/admin/addProduct/" +
          this.userID,
        {
          method: "post",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.product)
        }
      );
    },
  },
  created() {
    console.log('vvvvvvvvvvvvv');
    console.log(this.productParam);
    if(this.productParam){
      this.product = this.productParam;
      this.profileURL = this.product.image;
    }
  },
  
}
</script>
<style scoped>

.product{
    width: 90%;
    height: 600px;
    background-color: #EEEEEE;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    position: absolute;
    display: flex;
    justify-content: space-between;
    padding: 30px ;
}
.photo , .description{
    width: 40%;
}
.description{
    width: 50%;
    padding-right: 150px;
}
img{
    width: 100%;
    height: 100%;
}
.paddingwithborder {
  padding-bottom: 20px;
  border-bottom: 6px solid #ddd;
}

.changeprofile span {
  cursor: pointer;
  color: #ee1144;
  font-size: 18px;
}
.changeprofile .b-avatar {
  width: 4rem;
  height: 4rem;
  margin: 10px 10px 10px 0;
}

</style>