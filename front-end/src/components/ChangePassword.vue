<template>
    <div class="change">
        <Navbar />
        <div class="container">
        <div class=" changePassword" >
        <form>
            <div class="form-group">
                <label>Old Password</label>
                <input type="password"  v-model="form.oldPassword" class="form-control form-control-lg shadow-none" placeholder="old password" />
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password"  v-model="form.newPassword" class="form-control form-control-lg shadow-none" placeholder="new password" />
            </div>

            <div class="form-group">
                <label>Confirm Password</label>
                <input type="password"  v-model="confirmPassword" class="form-control form-control-lg shadow-none" placeholder="confirm Password" />
            </div>

            <div class="form-group ">
                <div>
              <button type="button" class="btn btn-primary"  @click.prevent="confirm">Save</button>
            <button type="button" class="btn btn-outline-dark" @click="cancel">Cancel</button>
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
    name: 'ChangePassword',
    components:{
        Navbar
    },
    data() {
        return {
            confirmPassword:'',
            form:{
                oldPassword:'',
                newPassword:'',
            }
        }
    },
    computed: {
        userID(){
            return this.$store.state.userID;
        }
    },
    methods: {
        parseText: function (resp) {
            return resp.text();
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
        CheckPassword(){
            return this.form.newPassword === this.confirmPassword;
        },
        cancel(){
            this.$router.push({ name: "Settings"});
        },
        async confirm(){
            if(this.CheckPassword()){
                try {
                    const response = await fetch( "http://localhost:8080/admin/changePassword/" + this.userID , {
                        method: "post",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(this.form)
                    }).then(this.checkStatus)
                    .then(this.parseJSON);

                    if(response){
                        this.$router.push({ name: "Settings"});
                    }else{
                        alert("Wrong password");
                    }
                } catch (error) {
                     alert("Error in changing password\n Please try again");
                }
            }else{
                alert("passwords are not identical");
            }
        }
    },
}
    
</script>

<style scoped>  
  
    label {
        font-weight: 500;
    }
    .changePassword{
        display: block;
        background-color: #FFF;
        height: auto;
        width: 500px;
        padding: 20px;
        border-radius: 5px;
    }
    form h3{
        margin-bottom: 0;
    }
    form span {
        font-size: 13px;
    }

    .form-group{
        display: flex;
        flex-direction: row;
        justify-content: left;
        align-items: center;
        margin-top: 15px;
        font-size: 10px;
    }
    .form-group label{
        width: 80px;
    }
    .form-group input{
        font-size: 13px;
        padding: 10px;
    }

    .form-control:focus,
    .btn:focus,.btn:active {
        border-color:#CCC;
        outline:0 !important;
        -webkit-box-shadow:none;
        box-shadow:none;
    }


    .btn-group-lg>.btn, .btn-lg 
    {width: 100%;}
.container
{
    width: 35%;
    padding-top: 30px;
}
div button {
  margin-right: 20px;
  min-width: 100px;
}
 
</style>