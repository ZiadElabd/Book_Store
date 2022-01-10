<template>
  <div>
    <Navbar />
     <div class="add">
       <router-link to="/newOrder" title="Add new Order" > <span>+</span></router-link>
    </div>
    <div class="orders">
        <table>
       <thead>
           <tr>
               <th>Order Id</th>
               <th>ISBN</th>
               <th>no.of copies</th>
               <th>Control</th>
           </tr>
       </thead>
       <tbody>
            <tr  v-for="order in orders" :key="order.isbn">
               <td>{{order.orderID}}</td>
               <td>{{order.isbn}}</td>
               <td>{{order.noOfCopies}}</td>
               <td><span @click="deleteOrder(order.orderID)" class="pink">Delete</span></td>
           </tr>
      </tbody>
   </table>
    </div>
  </div>
</template>

<script>
import Navbar from "../components/nbar.vue";
export default {
  name: "Orders",
  components: {
    Navbar,
  },
  data() {
    return {
      orders:[],
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
    async getOrders(){
      try {
          let response = await fetch( "http://localhost:8080/admin/getOrder/" + this.userID, {
              method: "get", 
          }).then(this.checkStatus)
          .then(this.parseJSON);
          console.log(response);
          this.orders = response;
      } catch (error) {
          alert('error');
      }
    },
    deleteOrder(orderId){
      let v = [];
      for(order in this.orders){
        if(order.orderId !== orderId)
          v.push(order);
      }
      this.orders = v;
      try {
          fetch( "http://localhost:8080/admin/deleteOrder/" + this.userID + '/' + orderId, {
              method: "delete", 
          })
      } catch (error) {
          alert('error');
      }
    }
  },
  created() {
    this.getOrders();
  },
}
</script>
<style scoped>
.orders{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
}
* {
  box-sizing: border-box;
}
/* The Table Attributes */
table {
  text-align: center;
  width: 600px;
  margin: 20px auto;
  font-family: sans-serif;
  border-bottom: 5px solid #009688;
}
/* The Attributes that the Head and the Body of the Table Share */
th,
td {
  padding: 10px;
}
/* Attributes of the Head of the Body */
th {
  background-color: #5C636A;
  color: white;
}
td {
  background-color: #eee;
}
span {
  padding: 5px 10px;
  margin: 3px;
  color: white;
  cursor: pointer;
}
.blue {
  background-color: #03a9f4;
}
.pink {
  background-color: #e91e63;
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
  top: -12px;
  left: 4px;
}
</style>
