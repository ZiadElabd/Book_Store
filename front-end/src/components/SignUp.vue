<template>
    <div class="container">
        <div class=" signUp" >
        <form>
            <h3>Sign Up</h3>
            <span>Already have an account? </span>  <router-link to="/">Log in</router-link>
            <div class="form-group">
                <label>First Name</label>
                <input type="text" placeholder="Enter your first name"  v-model="user.firstName" class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Last Name</label>
                <input type="text" placeholder="Enter your last name"  v-model="user.lastName" class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>userName</label>
                <input type="email"  v-model="user.userName" class="form-control form-control-lg" placeholder="Username" />
            </div>


            <div class="form-group">
                <label>Password</label>
                <input type="password"  v-model="user.password" class="form-control form-control-lg shadow-none" placeholder="password" />
            </div>

            <div class="form-group">
                <label>Confirm Password</label>
                <input type="password"  v-model="confirmPassword" class="form-control form-control-lg shadow-none" placeholder="confirm Password" />
            </div>
            <div class="form-group">
                <label>Address</label>
                <input type="text"  v-model="user.address" class="form-control form-control-lg" placeholder="Address" />
            </div>
             <div class="form-group">
                <label>Phone</label>
                <input type="text"  v-model="user.phone" class="form-control form-control-lg" placeholder="Phone number" />
            </div>

            <div class="form-group ">
                 <b-button variant="primary" @click.prevent="SignUp" class="btn  btn-lg btn-full "> Sign Up </b-button>
            </div>
        </form>
            
    </div>
    </div>
</template>

<script>
    export default {
        name: 'SignUp',
        data() {
            return {
                confirmPassword:'',
                user: {
                    firstName:'',
                    lastName:'',
                    userName:'',
                    password:'',
                    phone:'',
                    address:'',
                },
            }
        },
        methods:{
            parseText: function (resp) {
                return resp.text();
            },
            checkStatus: function (resp) {
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
            CheckNames(){
                return this.user.firstName.length > 0 && this.user.lastName.length > 0
            },
            async CheckUserName(){
                try{
                    console.log(this.user.userName);
                    const response = await fetch("http://localhost:8080/checkUsername/" + this.user.userName,{ 
                        method: "get",
                        headers: {'Content-Type': 'application/json'}
                    }).then(this.checkStatus)
                    .then(this.parseText);
                    console.log("response of check userName: " + response);
                    return response;
                }catch(error){
                    return false;
                }
            },
            CheckPassword(){
                return this.user.password === this.confirmPassword;
            },
            async SignUp(){
                console.log('in sign up')
                if(this.CheckNames() && await this.CheckUserName() && this.CheckPassword()){
                    console.log("sending sign up request");
                    const response = await fetch("http://localhost:8080/signup", {
                        method: "post",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(this.user),
                    }).then(this.checkStatus)
                    .then(this.parseText);

                    console.log(response);

                    if(response){
                        alert("Signed up seccessfully");
                        this.$router.push({ name: "SignIn" });
                    }else{
                        alert("Failed to SignUp");
                    }
                }else{
                    if(!this.CheckNames()){
                        alert("Invalid First Name or Last Name");
                    }else if(!this.CheckPassword()){
                        alert("Please, Enter a Valid Password with a length of 8");
                    }
                }
            },
        }
    }
</script>

<style scoped>  
  
    label {
        font-weight: 500;
    }
    .signUp{
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
 
</style>