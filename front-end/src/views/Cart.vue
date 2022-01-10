<template>
  <div>
    <Navbar />
    <div class="container">
      <div class="shopping-cart">
        <div class="column-labels">
          <label class="product-image">Image</label>
          <label class="product-details">Product</label>
          <label class="product-price">Price</label>
          <label class="product-quantity">Quantity</label>
          <label class="product-removal">Remove</label>
          <label class="product-line-price">Total</label>
        </div>
         <div class="product" v-for="product in products" :key="product.isbn">
            <div class="product-details">
                <div class="product-title">{{product.title}}</div>
                <div class="product-image"><img  :src="product.image"></div>
                <div>ISBN: {{product.isbn}}$</div>     
                <div>Category Name: {{product.categoryName}}$</div>  
                <div>Publisher: {{product.publisherName}}$</div> 
                <div>Publication Year: {{product.publicationYear}}$</div>
            </div>
         </div>

        <div class="totals">
          <div class="totals-item">
            <label>Subtotal</label>
            <div class="totals-value" id="cart-subtotal">71.97</div>
          </div>
          <div class="totals-item">
            <label>Tax (5%)</label>
            <div class="totals-value" id="cart-tax">3.60</div>
          </div>
          <div class="totals-item">
            <label>Shipping</label>
            <div class="totals-value" id="cart-shipping">15.00</div>
          </div>
          <div class="totals-item totals-item-total">
            <label>Grand Total</label>
            <div class="totals-value" id="cart-total">90.57</div>
          </div>
        </div>

        <button class="checkout">Checkout</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return{
        products:[]
    }
  },
  mounted: {},
  computed: {
    isAdmin() {
      return this.$store.state.role;
    },
    product() {
      return this.$route.params.product;
    },
    userID() {
      return this.$store.state.userID;
    },
  },
  methods: {
    parseJSON: function (resp) {
      return resp.json();
    },
    checkStatus: function (resp) {
      console.log("status");
      console.log(resp);
      if (resp.status >= 200 && resp.status < 300) {
        console.log("good status");
        return resp;
      }
      console.log("bad status");
      return this.parseJSON(resp).then((resp) => {
        throw resp;
      });
    },
    async deleteFromCart(product) {
      try {
        fetch(
          "http://localhost:8080/user/deleteFromCart/" +
            this.userID +
            "/" +
            product.isbn,
          {
            method: "delete",
          }
        );
      } catch (error) {
        alert("error");
      }
    },
    async getCart() {
      try {
        let response = await fetch(
          "http://localhost:8080/user/getCart/" + this.userID,
          {
            method: "get",
          }
        )
          .then(this.checkStatus)
          .then(this.parseJSON);
        console.log(response);
        this.products = response;
        console.log(this.products);
      } catch (error) {
        alert("error");
      }
    },
    incrementQuantity(product) {
      console.log(product.noOfCopies);
      fetch("http://localhost:8080/user/updateCart/" + this.userID, {
        method: "post",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          isbn: this.product.isbn,
          noOfCopies: product.noOfCopies,
        }),
      });
    },
  },
  created() {
    this.getCart();
  },
};

</script>
<style scoped>
.product-image {
  float: left;
  width: 20%;
}

.product-details {
  float: left;
  width: 37%;
}

.product-price {
  float: left;
  width: 12%;
}

.product-quantity {
  float: left;
  width: 10%;
}

.product-removal {
  float: left;
  width: 9%;
}

.product-line-price {
  float: left;
  width: 12%;
  text-align: right;
}

/* This is used as the traditional .clearfix class */
.group:before,
.shopping-cart:before,
.column-labels:before,
.product:before,
.totals-item:before,
.group:after,
.shopping-cart:after,
.column-labels:after,
.product:after,
.totals-item:after {
  content: "";
  display: table;
}
.shopping-cart[data-v-c028c34c] {
  margin-top: 73px;
}
.group:after,
.shopping-cart:after,
.column-labels:after,
.product:after,
.totals-item:after {
  clear: both;
}

.group,
.shopping-cart,
.column-labels,
.product,
.totals-item {
  zoom: 1;
}

/* Apply clearfix in a few places */
/* Apply dollar signs */
.product .product-price:before,
.product .product-line-price:before,
.totals-value:before {
  content: "$";
}

/* Body/Header stuff */
body {
  padding: 0px 30px 30px 20px;
  font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue",
    Helvetica, Arial, sans-serif;
  font-weight: 100;
}

h1 {
  font-weight: 100;
}

label {
  color: #aaa;
}

.shopping-cart {
  margin-top: -45px;
}

/* Column headers */
.column-labels label {
  padding-bottom: 15px;
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
}
.column-labels .product-image,
.column-labels .product-details,
.column-labels .product-removal {
  text-indent: -9999px;
}

/* Product entries */
.product {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}
.product .product-image {
  text-align: center;
}
.product .product-image img {
  width: 100px;
}
.product .product-details .product-title {
  margin-right: 20px;
  font-family: "HelveticaNeue-Medium", "Helvetica Neue Medium";
}
.product .product-details .product-description {
  margin: 5px 20px 5px 0;
  line-height: 1.4em;
}
.product .product-quantity input {
  width: 40px;
}
.product .remove-product {
  border: 0;
  padding: 4px 8px;
  background-color: #c66;
  color: #fff;
  font-family: "HelveticaNeue-Medium", "Helvetica Neue Medium";
  font-size: 12px;
  border-radius: 3px;
}
.product .remove-product:hover {
  background-color: #a44;
}

/* Totals section */
.totals .totals-item {
  float: right;
  clear: both;
  width: 100%;
  margin-bottom: 10px;
}
.totals .totals-item label {
  float: left;
  clear: both;
  width: 79%;
  text-align: right;
}
.totals .totals-item .totals-value {
  float: right;
  width: 21%;
  text-align: right;
}
.totals .totals-item-total {
  font-family: "HelveticaNeue-Medium", "Helvetica Neue Medium";
}

.checkout {
  float: right;
  border: 0;
  margin-top: 20px;
  padding: 6px 25px;
  background-color: #6b6;
  color: #fff;
  font-size: 25px;
  border-radius: 3px;
}

.checkout:hover {
  background-color: #494;
}
</style>