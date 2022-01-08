<template>
  <div>
    <Navbar />
    <div class="container">
      <div class="Settings">
        <form>
          <div class="mb-3">
            <label for="firstname" class="form-label">first Name</label>
            <input
              type="text"
              class="form-control"
              id="firstname"
              v-model="allSettings.firstName"
            />
          </div>
          <div class="mb-3">
            <label for="lastname" class="form-label">last Name</label>
            <input
              type="text"
              class="form-control"
              id="lastname"
              v-model="allSettings.lastName"
            />
          </div>
         
          <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input
              type="text"
              class="form-control"
              id="username"
               v-model="allSettings.userName"
              disabled
            />
          </div>
          <div class="mb-3">
            <label for="address" class="form-label">Address</label>
            <input
              type="text"
              class="form-control"
              id="address"
              v-model="allSettings.address"
            />
          </div>


          <div class="mb-3">
            <label for="Phone" class="form-label">Phone</label>
            <input
              type="text"
              class="form-control"
              id="Phone"
              v-model="allSettings.phone"
            />
          </div>

          <div id="contacts">
  <table>
    <thead>
      <tr>
        <th v-if="role === 1" class="sort" data-sort="name">Admins</th>
      </tr>
    </thead>
    <tbody v-if="role === 1" class="list">
      <tr v-for="admin in admins" :key="admin">
        <td class="name">{{admin}}</td>
        <td @click="deleteAdmin(admin)" class="remove" ><i class="fa fa-trash" aria-hidden="true"></i></td>
      </tr>
    </tbody>
  </table>
 
</div>
          <div class="save_and_cancel">
           <div>
              <button type="button" class="btn btn-primary"  @click.prevent="saveSetting">Save</button>
            <button type="button" class="btn btn-outline-dark" @click="cancel">Cancel</button>
           </div>
           <div>
              <router-link to="/ChangePassword">Change Password</router-link>
              <router-link v-if="role === 1" to="/admins">Admins</router-link>
           </div>
          </div>
        </form>
      </div>
    </div>
    
  </div>
</template>

<script>
import Navbar from "../components/nbar.vue";
export default {
  name: "Settings",
  isAdmin: true ,
  components: {
    Navbar,
  },
  data() {
    return {
      newAdmin:'',
      allSettings: {
        firstName:"",
        lastName:"", 
        address: "",
        phone: "",
        userName: ""
      },
      admins: [],
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
    async getSetting(){
      try {
          let response = await fetch( "http://localhost:8080/admin/getSetting/" + this.userID, {
              method: "get", 
          }).then(this.checkStatus)
          .then(this.parseJSON);
          console.log(response);
          this.allSettings = response;
      } catch (error) {
          alert('error');
      }
    },
    async getAdmins(){
      try {
          let response = await fetch( "http://localhost:8080/admin/getAdmins/" + this.userID, {
              method: "get", 
          }).then(this.checkStatus)
          .then(this.parseJSON);
          console.log(response);
          this.admins = response;
          console.log(this.admins);
      } catch (error) {
          alert('error');
      }
    },
    async deleteAdmin(userName){
      this.admins = this.admins.filter(item => item !== userName);
      try {
          fetch( "http://localhost:8080/admin/deleteAdmin/" + this.userID + '/' + userName, {
              method: "delete", 
          })
      } catch (error) {
          alert('error');
      }
    },
    saveSetting(){
      fetch(
        "http://localhost:8080/admin/updateAdmin/" + this.userID,
        {
          method: "put",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.allSettings),
        }
      );
      this.$router.push({ name: "Products"});
    },
    cancel(){
      this.$router.push({ name: "Products"});
    }
  },
  created() {
    this.getSetting();
    this.getAdmins();
  },
};
</script>
<style scoped>
.Settings {
  background-color: #DDD;
  border-radius: 10px;
  padding: 20px;
  top: 50px;
  position: absolute;
  width: 40%;
}
.container {
  width: 45%;
}
select {
  margin: 10px 20px 0 0;
  padding: 3px;
  height: 40px;
}
.paddingwithborder {
  padding-bottom: 20px;
  border-bottom: 6px solid #ddd;
}
.changecover {
  padding: 15px 0;
}
.changeprofile span,
.changecover span {
  cursor: pointer;
  color: #ee1144;
  font-size: 18px;
}
.changeprofile .b-avatar,
.changecover .b-avatar {
  width: 4rem;
  height: 4rem;
  margin: 10px 10px 10px 0;
}
.save_and_cancel {
  margin: 20px 0;
  display: flex;
  justify-content: space-between;
}
.save_and_cancel button {
  margin-right: 20px;
  min-width: 100px;
}
.save_and_cancel a {
  display: block;
}
i{
  cursor: pointer;
}
td{
  width: 300px;
}
td input{
  width: 200px;
}
.add[data-v-53cc84dd] {
    display: block;
    margin-left: 80px;
}

</style>
