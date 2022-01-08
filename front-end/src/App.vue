<template>
  <div id="app">

    
       
  <router-view/>
    
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {    

      }
}
</script>

<style >
#app
{
  background-color: #FFF;
  min-width: 100%;
  min-height: 100%;
}
.navbar
{
  display: flex;
  justify-content: space-between;
  background-color: #131619;
  z-index: 10000000;
  
}

</style>


