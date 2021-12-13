<template>
  <div id="home_page_frame">
    <nav class="navbar-frame">
      <nav-bar>
        <template v-slot:search>
          <search @onClick="search"/>
        </template>
      </nav-bar>
    </nav>
    <div class="home-page">
      <div class="home-page-container">
        <div class="el-carousel">
          <el-carousel :interval="4000" type="card" height="335px">
            <el-carousel-item v-for="(item,index) in carouselData" :key="index">
              <div @click="routerToProductPage(item.productId)" class="carousel-content"
                   :style="'background-image: url('+item.productCoverUrl+')'">
                <!--                {{item}}-->
              </div>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="content-card">
          <h2 class="content-title">{{ $t('page.allBooks') }}</h2>
          <div class="content-item">
            <ul v-if="productData.length>0" class="content-ul">
              <li @click="routerToProductPage(item.id)" class="content-li" v-for="(item,index) in productData"
                  :key="index">
                <div class="background-img">
                  <img referrerpolicy="never" :src="item.coverUrl" alt="">
                </div>
                <div class="item-intro-box">
                  <span>
                    {{ item.name }}
                    <span class="item-score">
                     {{ item.score.total === 0 ? '0.0' : (item.score.score / item.score.total).toFixed(1) }}
                    </span>
                  </span>
                  <p class="item-introduction">
                    {{ item.introduction.substr(0, 25) + '……' }}
                  </p>
                </div>
              </li>
            </ul>
            <h3 v-else style="font-size: 1.2rem; font-weight: 100; margin: 1rem 0">暂无数据</h3>
          </div>
        </div>
      </div>
      <footer class="footer-bar-frame">
        <copyright/>
      </footer>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/nav-bar';
import Copyright from '@/components/copyright';
import Search from '@/components/search';

export default {
  name: "index",
  data() {
    return {
      carouselData: [],
      productData: [],
    }
  },
  components: {
    NavBar, Copyright, Search
  },
  methods: {
    routerToProductPage(id) {
      this.$router.push('/product-information?productId=' + id)
    },
    search(keyword) {
      this.api.dBookClientGetAllBooks(0, 0, keyword).then(rs => {
        this.productData = rs.data.data;
        console.log(this.productData);
      })
    }
  },
  created() {
    this.api.dBookGetWebsiteIndexBooks(1).then(rs => {
      this.carouselData = rs.data.data;
    })
    this.api.dBookClientGetAllBooks(0, 0, 0).then(rs => {
      this.productData = rs.data.data;
    })
  }
}
</script>

<style scoped lang="scss">
#home_page_frame {
  width: 100%;
  height: 100%;
  //background-color: #008489;

  .navbar-frame {
    width: 100%;
    height: 11.8%;
    min-height: 5rem;
    box-shadow: 0 0 2px rgba(66, 66, 66, .56);
  }

  .home-page {
    width: 100%;
    height: calc(100% - 11.8%);
    max-height: calc(100% - 5rem);
    overflow: auto;

    #search_bar {
      width: 100%;
      height: 3rem;
      margin: .85rem auto;
      display: none;
    }

    .home-page-container {
      height: auto;

    }

    .footer-bar-frame {
      width: 100%;
      height: auto;
    }
  }
}

.el-carousel {
  width: 95%;
  margin: 1.5rem auto 0;
  //box-shadow: 0 3px 3px rgba(22, 22, 22, .38);
}

.carousel-content {
  width: 100%;
  height: 100%;
  background-size: 100% 100%;
  background-position: center center;
  border: 1px solid rgba(22, 22, 22, .38);
  //border-radius: 1rem;
}

//.el-carousel__item:nth-child(2n) {
//  background-color: #99a9bf;
//}
//
//.el-carousel__item:nth-child(2n+1) {
//  background-color: #d3dce6;
//}

.content-card {
  width: 85%;
  margin: 2rem auto;

  h2 {
    width: 100%;
    text-align: center;
    padding: .5rem 0;
    //font-size: 1.5rem;
    color: rgba(22, 22, 22, 0.95);
  }

  .content-item {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .content-ul {
      width: 100%;
      display: grid;
      justify-items: center;
      grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
      list-style: none;

      .content-li {
        width: 12rem;
        height: 16rem;
        margin: 1rem 0;
        background-size: 100% 100%;
        background-position: center center;
        border: 1px solid rgba(22, 22, 22, .38);
        box-shadow: 0 0 12px rgba(22, 22, 22, .42);
        border-radius: 2px;
        cursor: pointer;
        position: relative;
        transition: .75s ease;

        .background-img {
          width: 100%;
          height: 100%;
          overflow: hidden;

          img {
            width: 100%;
            height: 100%;
          }
        }

        .item-intro-box {
          color: rgba(255, 201, 119, .85);
          width: 100%;
          padding: 2%;
          height: auto;
          font-weight: 400;
          background-color: rgba(22, 22, 22, .65);
          //border-top: 1px solid rgba(240, 240, 240, .38);
          position: absolute;
          bottom: 0;
          right: 0;
          font-size: .85rem;
        }

        .item-score {
          //display: block;
          //padding: .25rem .5rem;
          color: rgba(255, 201, 119, .85);
          font-weight: 900;
          font-size: .85rem;
          text-align: right;
          //text-shadow: 0 0 3px (255, 255, 255, .05);
          //background-color: rgba(255, 255, 255, .05);
        }

        .item-introduction {
          padding-top: .2rem;
          color: #ffffff;
          font-size: .5rem;
          font-weight: 100;
        }
      }
    }
  }
}

@media screen and (max-width: 1024px) {
  #home_page_frame {

    .navbar-frame {
      height: 11.5%;
    }

  }
}

@media screen and (max-width: 878px) {
  #home_page_frame {

    .home-page {
      #search_bar {
        display: block;
      }
    }
  }
}

@media screen and (max-width: 600px) {
  #home_page_frame {
    .navbar-frame {
      height: 10.5%;
    }

    //background-color: violet;
  }
}

@media screen and (max-width: 480px) {
  #home_page_frame {
    //background-color: saddlebrown;
  }
}
</style>
