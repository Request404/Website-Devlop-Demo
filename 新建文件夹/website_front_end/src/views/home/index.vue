<template>
  <div id="home_page_frame">
    <nav class="navbar-frame">
      <nav-bar/>
    </nav>
    <div class="home-page-container">

    </div>
    <footer class="footer-bar-frame">

    </footer >
  </div>
</template>

<script>
import NavBar from '../../components/nav-bar'

export default {
  name: "index",
  components: {
    NavBar
  }
}
</script>

<style scoped lang="scss">
#home_page_frame{
  width: 100%;
  height: 100%;
  background-color: #008489;

  .navbar-frame{
    width: 100%;
    height: 13.8%;
  }

  .home-page-container{

  }

  .footer-bar-frame{

  }
}


@media screen and (max-width:1024px){
  #home_page_frame{
    background-color: violet;
  }
}
@media screen and (max-width:600px){
  #home_page_frame{
    background-color: darkcyan;
  }
}
@media screen and (max-width:480px){
  #home_page_frame{
    background-color: saddlebrown;
  }
}
</style>
