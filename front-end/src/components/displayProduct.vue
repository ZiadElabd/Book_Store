<template>
       <div>
            <Navbar />
            <div class="product">
                <div class="photo">
                    <img :src="product.image" alt="">
                </div>
                <div class="description">
                <div class="name">
                        <h1>{{product.title}}</h1>
                </div>

                    
                    <div class="price">
                        <div>ISBN: {{product.isbn}}</div>     
                        <div>no.of Copies: {{product.noOfCopies}}</div>
                        <div>Price: {{product.price}}$</div>   
                        <div>Category Name: {{product.categoryName}}</div>  
                         <div>Publisher: {{product.publisherName}}</div> 
                         <div>Publication Year: {{product.publicationYear}}</div> 
                    </div>
                    <br>
                    <button type="button" class="btn btn-primary" v-if="! isAdded" @click.prevent="addToCart">ADD TO CART</button>
                    <button type="button" class="btn btn-primary" v-else  disabled >Added</button>
                </div>
            </div>
       </div>
</template>
<script>
import Navbar from "../components/nbar.vue";
export default {
    components:{
        Navbar
    },
    data(){
        return{
            isAdded: false,
        }
    },
    computed: {
        isAdmin(){
            return this.$store.state.role;
        },
        product(){
            return this.$route.params.product;
        },
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
        addToCart(){
            fetch(
            "http://localhost:8080/user/addToCart/" + this.userID,
            {
                method: "post",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    isbn: this.product.isbn,
                    noOfCopies: 1
                })
            });
            alert('The book is added to cart');
        },
        async checkInCart(){
            let response = await fetch(
            "http://localhost:8080/user/checkCart/" + this.userID + "/" + this.product.isbn ,
            {
                method: "get",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(this.product)
            }).then(this.checkStatus)
            .then(this.parseText);
            this.isAdded = response;
        }
    },
    created() {
        console.log("created");
        console.log(this.product);
        this.checkInCart();
        console.log(this.isAdded);
    },

}
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Rubik:wght@500&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Prompt&family=Rubik:wght@500&family=Titillium+Web:ital,wght@0,200;1,300&display=swap');
.product{
    width: 70%;
    height: 400px;
    background-color: #EEEEEE;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    position: absolute;
    display: flex;
    justify-content: space-between;
    position: abslute;
font-family: Roboto,Helvetica Neue,Helvetica,Tahoma,Arial,PingFang SC,Microsoft YaHei;
}
.photo , .description{
    width: 40%;
}
.description{
    position: absolute;
    top: 50%;
    left: 75%;
    transform: translate(-50%,-50%);
    width: 50%;
}
img{
    width: 100%;
    height: 100%;
}
.name , .price 
{
    width: 80%;
    padding: 20px 0;
    border-bottom: 4px solid #DDD;
}
.name h1{
    font-family: 'Rubik', sans-serif;
}

</style>