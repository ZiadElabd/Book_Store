<template>
  <div>
    <Navbar />
     <div class=" signin" >
        <form>
           
            <div class="form-group">
                <label>ISBN</label>
                <input type="text" v-model="order.isbn" class="form-control form-control-lg" placeholder="ISBN" />
            </div>

            <div class="form-group">
                <label>No.of Copies</label>
                <input type="text"  v-model="order.noOfCopies" class="form-control form-control-lg shadow-none" placeholder="Copies" />
            </div>
            <div class="form-group ">
                 <b-button variant="primary" @click="addNewOrder" class="btn  btn-lg btn-full "> Add new Order</b-button>
        </div>
        </form>
            
    </div>
    
  </div>
</template>

<script>
import Navbar from "../components/nbar.vue";
export default {
  name: "newOrder",
  components: {
    Navbar,
  },
  data() {
    return {
     order:{
       isbn: '',
       noOfCopies:''
     }
    };
  },
  computed:{
    userID(){
      return this.$store.state.userID;
    },
    userName(){
      return this.$store.state.userName;
    },
    role(){
      return this.$store.state.role;
    },
  },
  methods: {
    addNewOrder(){
        fetch(
          "http://localhost:8080/admin/insertOrder/" + this.userID,
          {
            method: "post",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(this.order)
          }
        );
        this.$router.push({ name: "Orders" });
    }
  }
}
</script>
<style scoped>

</style>
