<template>
    <div class="container">
        <div class=" signin" >
        <form>
            <div class="icon">
                <img src="../assets/market.jpg" alt="">
            </div>
            <div class="form-group">
                <label>Login</label>
                <input type="email" v-model="form.userName" class="form-control form-control-lg" placeholder="Username or e-mail" />
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" v-model="form.password" class="form-control form-control-lg shadow-none" placeholder="password" />
            </div>
            <div class="form-group ">
                 <b-button variant="primary" @click.prevent="SignIn" class="btn  btn-lg btn-full "> Log in </b-button>
                 <b-button variant="light" class="btn  btn-lg btn-full "><router-link to="/signup">Sign Up</router-link></b-button>
        </div>
        </form>
            
    </div>
    </div>
</template>

<script>
    export default {
        name: 'SignIn',
        data() {
            return {
                form:{
                    userName:'',
                    password:'',
                }
            }
        },
        methods:{
            parseJSON: function (resp) {
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
            async login(){
                try {
                    const response = await fetch( "http://localhost:8080/signin/" , {
                        method: "post",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(this.form)
                    }).then(this.checkStatus)
                    .then(this.parseJSON);

                    //console.log(response);
                    let p = JSON.parse(response);
                    console.log(p.role + " "  + p.sessionID);

                    this.$store.commit('saveUserData',{
                        _id: p.sessionID,
                        _name: this.form.userName,
                        _role: p.role
                    });
                    console.log("vvvvvvvv");
                    console.log("user name that stored in the Vuex" + this.$store.state.userID);
                    return {
                        valid: true,
                        role: p.role
                    };
                } catch (error) {
                    return {
                        valid: false,
                        role: null
                    };
                } 
            },
            async SignIn(){
                const state = await this.login();
                if (state.valid) {
                    if(state.role)
                        this.$router.push({ name: "Products"});
                    else
                        this.$router.push({ name: "Products"});
                } else {
                    alert("Please try agian, email or password is wrong :(");
                }
            },
        }
    }
</script>
<style scoped>
.container
{
    width: 30%;
    padding-top: 30px;
    }
.form-group
{
    margin-bottom: 10px;
}
.icon{
    width: 200px;
    height: 200px;
    margin: auto;
}

.icon img{
    width: 100%;
    height: 100%;
}
[type=button]:not(:disabled), [type=reset]:not(:disabled), [type=submit]:not(:disabled), button:not(:disabled){
    margin: 10px 0;
}
a {
    color: inherit;
    text-decoration: none;
}



</style>