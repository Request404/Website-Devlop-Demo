<template>
  <div class="copyright-frame">
    <div id="operation_list_container">
      <div id="logo">
        <img :src="logo" alt="">
      </div>
      <div id="operation_list">
        <ol class="operation-ol">
          <li class="operation-li operation-li-header">{{ $t("copyright.websiteUser") }}</li>
          <li class="operation-li"><a href="https://mail.qq.com">{{ $t("copyright.linkUs") }}</a></li>
          <li class="operation-li"><span class="span-button">{{ $t("copyright.complaints") }}</span></li>
          <li class="operation-li"><span class="span-button">{{ $t("copyright.userAgreement") }}</span></li>
          <li class="operation-li"><a href="https://www.apache.org/licenses/LICENSE-2.0">{{
              $t("copyright.openSource")
            }}</a></li>
        </ol>
        <ol class="operation-ol">
          <li class="operation-li operation-li-header">{{ $t("copyright.websiteData") }}</li>
          <li class="operation-li">
            <a :href="$store.state.backEndUrl+'豆瓣电影爬虫.html'">
              {{ $t("copyright.dataCapture") }}
            </a>
          </li>
          <li class="operation-li"><a href="https://book.douban.com/">{{ $t("copyright.booksDataSource") }}</a></li>
          <li class="operation-li"><a href="https://movie.douban.com/">{{ $t("copyright.moviesDataSource") }}</a></li>
          <li class="operation-li"><a href="https://music.douban.com/">{{ $t("copyright.musicsDataSource") }}</a></li>
        </ol>
        <ol class="operation-ol">
          <li class="operation-li operation-li-header">{{ $t("copyright.aboutWebsite") }}</li>
          <li class="operation-li"><a
              href="https://github.com/Request404/WebsiteDesginFor4">{{ $t("copyright.sourceCodeUrl") }}</a></li>
          <li class="operation-li"><a
              :href="$store.state.backEndUrl+'swagger-ui/index.html'">{{ $t("copyright.apiDocument") }}</a></li>
          <li class="operation-li"><a :href="$store.state.backEndUrl+'druid/login.html'">{{ $t("copyright.dataSource") }}</a></li>
          <li class="operation-li"><span class="span-button" @click="$router.push('/administration/login')">{{ $t("copyright.adminClient") }}</span></li>
        </ol>
      </div>
      <div id="d_code">
        <img :src="dCode" alt="">
      </div>
    </div>
    <div id="copyright_container">
      <div>
        <span> {{ $t("index.email") }}：<a href="https://mail.qq.com">Request404@qq.com</a></span>
        <span> {{ $t("index.licenses") }}：<a href="https://www.apache.org/licenses/LICENSE-2.0">Apache 2.0</a></span>
        <span><a href="#"> {{ $t("index.support") }}!</a></span>
      </div>
      <div>
        <span> COPYRIGHT © 2021 Request 404. ALL RIGHTS RESERVED.</span>
      </div>
    </div>
  </div>
</template>

<script>
import logo from '../../assets/img/common/logo.png'
import dCode from '../../assets/img/common/d-code.png'

export default {
  name: "index",
  data() {
    return {
      logo, dCode,
    }
  }
}
</script>

<style scoped lang="scss">
.copyright-frame {
  width: 100%;
  height: auto;
  box-shadow: 0 0 5px rgba(66, 66, 66, .35);
  background-image: linear-gradient(to bottom, rgba(22, 22, 22, .85), rgba(22, 22, 22, .88), rgba(22, 22, 22, .9));
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-shadow: 0 0 1px rgba(255, 255, 255, .58);


  #operation_list_container {
    width: 80%;
    padding: 1rem 0;
    display: flex;
    justify-content: center;
    border-bottom: 1px solid rgba(255, 255, 255, .35);

    #logo {
      width: 6.8rem;
      height: auto;
      margin: 0 3rem;

      img {
        width: 100%;
        height: auto;
      }
    }

    #d_code {
      width: 9.28rem;
      height: auto;
      margin: .5rem;

      img {
        width: 100%;
        height: auto;
      }
    }

    #operation_list {
      width: 70%;
      display: grid;
      grid-template-columns: 1fr 1fr 1fr;

      .operation-ol {
        list-style: none;

        .operation-li {
          font-size: 1rem;
          font-weight: 100;
          padding: .5rem;
          color: rgba(240, 240, 240, .95);
        }

        .operation-li-header {
          font-weight: bold;
          font-size: 1.15rem;
          color: white;
        }
      }
    }
  }

  #copyright_container {
    font-size: 1rem;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-weight: 100;
    padding: 1rem;
  }
}

a {
  color: rgba(240, 240, 240, .95);
}

.span-button{
  cursor: pointer;
}
</style>
