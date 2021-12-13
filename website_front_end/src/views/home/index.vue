<template>
  <div v-if="cubeData" id="home_page_frame">
    <nav class="navbar-frame">
      <nav-bar>
        <template v-slot:search>
          <search />
        </template>
      </nav-bar>
    </nav>
    <div class="home-page">
      <div class="home-page-container">
        <cube v-if="cubeData">
          <template v-slot:forward_scroll>
            <div @click="routerToProductPage(cubeData[0].productId)" class="cube-page" :style="'background-image: url('+cubeData[0].productCoverUrl+')'">
              <div class="cube-left">
                <span class="prod-name">
                  {{ cubeData[0].productData.name }}
                </span>
                <span class="prod-introduction">
                  {{ cubeData[0].productData.introduction.substr(0, 100) + '……' }}
                </span>
              </div>
            </div>
          </template>
          <template v-slot:left_scroll>
            <div @click="routerToProductPage(cubeData[1].productId)" class="cube-page" :style="'background-image: url('+cubeData[1].productCoverUrl+')'">
              <div class="cube-left">
                <span class="prod-name">
                  {{ cubeData[1].productData.name }}
                </span>
                <span class="prod-introduction">
                  {{ cubeData[1].productData.introduction.substr(0, 100) + '……' }}
                </span>
              </div>
            </div>
          </template>
          <template v-slot:right_scroll>
            <div @click="routerToProductPage(cubeData[2].productId)" class="cube-page" :style="'background-image: url('+cubeData[2].productCoverUrl+')'">
              <div class="cube-left">
                <span class="prod-name">
                  {{ cubeData[2].productData.name }}
                </span>
                <span class="prod-singer">
                  {{ cubeData[2].productData.album.substr(0, 100) }}
                  <br>
                  {{ cubeData[2].productData.singer.substr(0, 100) }}
                </span>
              </div>
            </div>
          </template>
          <template v-slot:back_scroll>
            <div @click="routerToProductPage(cubeData[1].productId)" class="cube-page" :style="'background-image: url('+cubeData[3].productCoverUrl+')'">
              <div class="cube-left">
                <span class="prod-name">
                  {{ cubeData[3].productData.name }}
                </span>
                <span class="prod-introduction">
                  {{ cubeData[3].productData.introduction.substr(0, 100) + '……' }}
                </span>
              </div>
            </div>
          </template>
        </cube>
        <div class="content-card">
          <h2 class="content-title">{{ $t('page.moviesRecommend') }}</h2>
          <div class="content-item">
            <ul class="content-ul">
              <li @click="routerToProductPage(item.productId)" class="content-li" v-for="(item,index) in moviesData.slice(0,10)" :key="index"
                  :style="'background-image: url('+item.productData.coverUrl+')'">
                <div class="item-intro-box">
                  <span>
                    {{ item.productData.name }}
                    <span class="item-score">
                      {{ item.score.total === 0 ? '0.0' : (item.score.score / item.score.total).toFixed(1) }}
                    </span>
                  </span>
                  <p class="item-introduction">
                    {{ item.productData.introduction.substr(0, 25) + '……' }}
                  </p>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div class="content-card">
          <h2>{{ $t('page.musicsRecommend') }}</h2>
          <div class="content-item">
            <ul class="content-ul">
              <li @click="routerToProductPage(item.productId)" class="content-li" v-for="(item,index) in musicsData.slice(0,10)" :key="index"
                  :style="'background-image: url('+item.productData.albumAvatarUrl+')'">
                <div class="item-intro-box">
                  <span>
                    {{ item.productData.name }}
                    <span class="item-score">
                      {{ item.score.total === 0 ? '0.0' : (item.score.score / item.score.total).toFixed(1) }}
                    </span>
                  </span>
                  <p class="item-introduction">
                    {{ item.productData.singer }}
                  </p>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div class="content-card">
          <h2>{{ $t('page.booksRecommend') }}</h2>
          <div class="content-item">
            <ul class="content-ul">
              <li @click="routerToProductPage(item.productId)" class="content-li" v-for="(item,index) in booksData.slice(0,10)" :key="index"
                  :style="'background-image: url('+item.productData.coverUrl+')'">
                <div class="item-intro-box">
                  <span>
                    {{ item.productData.name }}
                    <span class="item-score">
                      {{ item.score.total === 0 ? '0.0' : (item.score.score / item.score.total).toFixed(1) }}
                    </span>
                  </span>
                  <p class="item-introduction">
                    {{ item.productData.introduction.substr(0, 25) + '……' }}
                  </p>
                </div>
              </li>
            </ul>
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
import Cube from '@/components/cube';
import Search from '@/components/search'
// import ImgUrl from '../../assets/img/index/index01.jpg'
// import search from '@/components/search'

export default {
  name: "index",
  data() {
    return {
      cubeData: null,
      moviesData: [],
      booksData: [],
      musicsData: []
    }
  },
  components: {
    NavBar, Copyright, Cube, Search
  },
  methods: {
    routerToProductPage(id) {
      this.$router.push('/product-information?productId='+id)
    }
  },
  created() {
    this.api.dRecordGetIndexCubeRecommend().then(rs => {
      this.cubeData = rs.data.data;
    })
    this.api.dMovieGetWebsiteIndexMovies(0).then(rs => {
      this.moviesData = rs.data.data;
    })
    this.api.dBookGetWebsiteIndexBooks(0).then(rs => {
      this.booksData = rs.data.data;
    })
    this.api.dMusicGetWebsiteIndexMusics(0).then(rs => {
      this.musicsData = rs.data.data;
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

.cube-page {
  width: 100%;
  height: 100%;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-position: center center;

  .cube-left {
    width: 18%;
    height: 100%;
    padding: 0 3%;
    background-color: rgba(22, 22, 22, .76);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .prod-name {
      font-size: 1.35rem;
      color: #ffffff;
      //font-family: 楷体, serif;
      text-shadow: 0 0 2px rgba(255, 255, 255, .38);
      text-align: center;
      padding: .75rem 0;
    }

    .prod-singer {
      font-size: 1rem;
      color: rgba(240, 240, 240, .95);
      font-family: 楷体, serif;
      text-shadow: 0 0 2px rgba(255, 255, 255, .38);
      font-weight: 100;
    }

    .prod-introduction {
      font-size: 1rem;
      color: rgba(240, 240, 240, .95);
      font-family: 楷体, serif;
      text-shadow: 0 0 2px rgba(255, 255, 255, .38);
      text-indent: 2em;
      font-weight: 100;
    }
  }
}


.content-card {
  width: 85%;
  margin: 2rem auto;

  h2 {
    padding: .5rem 2.8rem;
    color: rgba(22, 22, 22, 0.95);
    font-weight: 400;
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
