<template>
  <div v-if="userInfo" id="home_page_frame">
    <nav class="navbar-frame">
      <nav-bar/>
    </nav>
    <div class="home-page">
      <div id="home_page">
        <div id="user_info">
          <Modal
              title="修改信息"
              v-model="modal"
              :styles="{top: '33%'}">
            <Input v-model="userInfo.nickName" placeholder="Enter number" style="width: 80%; margin: 5px">
              <span slot="prepend">{{ $t('userInfo.nickname') }}：</span>
            </Input>
            <div style="width: 80%; margin: 15px 5px; color: rgba(22, 22, 22,.75); display: flex">
              {{ $t('userInfo.gender') }}：
              <RadioGroup v-model="userInfo.gender" style="display: flex;">
                <Radio class="model-radio" style="color: #1296db" label="男">
              <span class="model-icon">
                <img :src="manSvg" style="width: 1.2rem; height: 1.2rem" alt="">
              </span>
                  <span>{{ $t('userInfo.male') }}</span>
                </Radio>
                <Radio id="woman_radio" class="model-radio" style="color: #db639b" label="女">
              <span class="model-icon">
                <img :src="womanSvg" style="width: 1.2rem; height: 1.2rem" alt="">
              </span>
                  <span>{{ $t('userInfo.female') }}</span>
                </Radio>
              </RadioGroup>
            </div>
            <Input disabled v-model="userInfo.email" placeholder="Enter number" style="width: 80%; margin: 5px">
              <span slot="prepend">{{ $t('userInfo.email') }}：&nbsp;&nbsp;</span>
            </Input>
            <Input v-model="userInfo.telephone" placeholder="Enter number" style="width: 80%; margin: 5px">
              <span slot="prepend">{{ $t('userInfo.contact') }}：</span>
            </Input>
            <div slot="footer">
              <Button type="success" size="large" :loading="modal_loading" @click="commit">提交</Button>
            </div>
          </Modal>
          <!--    {{userInfo}}-->
          <div id="avatar_frame">
            <img-upload :imgUrl="userInfo.headPortrait===null||''?defaultAvatar:userInfo.headPortrait"
                        @callBack="callBack">
            </img-upload>
          </div>
          <div id="user_info_form">
            <el-tooltip class="item" effect="light" :content="$t('userInfo.alterInfo')" placement="bottom-end">
              <img @click="modal = true" id="alter_icon" :src="alterSvg" alt="">
            </el-tooltip>
            <el-tooltip class="item" effect="light" :content="$t('userInfo.signOut')" placement="bottom-end">
              <img @click="signOut" id="logout_icon" :src="logoutSvg" alt="">
            </el-tooltip>
            <ul id="user_info_ul">
              <li class="user-info-li">
            <span>
              <b>{{ $t('userInfo.username') }}：</b>{{ userInfo.username }}
              <img :src="userInfo.gender==='男'?manSvg:womanSvg" alt="">
            </span>
              </li>
              <li class="user-info-li">
                <span><b>{{ $t('userInfo.nickname') }}：</b>{{ userInfo.nickName }}</span>
              </li>
              <li class="user-info-li">
                <span><b>{{ $t('userInfo.email') }}：</b>{{ userInfo.email }}</span>
              </li>
              <li class="user-info-li">
                <span><b>{{ $t('userInfo.contact') }}：</b>{{ userInfo.telephone }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="user-content-view">
        <h2>{{ $t('userInfo.myCollections') }}</h2>
        <div v-if="collectionsData&&collectionsData.length>0" class="content-item">
          <ul class="content-ul">
            <li @click="routerToProductPage(collection.productId)" class="content-li"
                v-for="(collection,index) in collectionsData" :key="index"
                :style="'background-image: url('+collection.productCoverUrl+')'">
              <div class="item-intro-box">
                  <span>
                    {{ collection.productName }}
                  </span>
                <p class="item-introduction">
                  {{ $t('userInfo.collectTime') }} {{ collection.collectTime }}
                </p>
              </div>
            </li>
          </ul>
        </div>
        <h3 v-else style="font-size: 1.2rem; font-weight: 100; margin: 1rem 0">{{ $t('userInfo.onCollection') }}</h3>
      </div>
      <div class="user-comments-view">
        <div class="comment-frame">
          <h2 class="comments-title">{{ $t('userInfo.myComments') }}</h2>
          <div class="comments-wrap">
            <ul class="comments-wrap-ul" v-if="commentsData&&commentsData.length>0">
              <li class="comments-wrap-li" v-for="(comment, index) in commentsData" :key="index">
                <div class="avatar-box">
                  <figure>
                    <img :src="comment.avatarUrl" alt="">
                  </figure>
                </div>
                <div class="comment-content">
                  <div class="comments-desc">
                    <span class="user-nickname">{{ comment.userNickname }}</span>
                    <span class="comment-time">{{ comment.commentTime }} </span>
                    <img v-if="userInfo?comment.userId === userInfo.id:false" @click="removeComment(comment)"
                         class="del-trash"
                         :src="trashSvg" alt="">
                  </div>
                  <el-rate
                      v-model="comment.score"
                      disabled
                      show-score
                      :colors="colors"
                      :text-color="comment.score>2?'#ff9900':'rgb(153, 169, 191)'"
                      score-template="{value}">
                  </el-rate>
                  <article class="the-comment">
                    {{ comment.comment }}
                  </article>
                </div>
              </li>
            </ul>
            <h3 v-else style="font-size: 1.2rem; font-weight: 100; margin: 1rem 0">{{ $t('page.score.noComment') }}</h3>
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
import imgUpload from '@/components/img-upload';
import defaultAvatar from '@/assets/img/home/avatar.gif';
import manSvg from '@/assets/img/svg/man.svg';
import womanSvg from '@/assets/img/svg/woman.svg';
import alterSvg from '@/assets/img/svg/alter.svg';
import logoutSvg from '@/assets/img/svg/user-logout.svg';
import trashSvg from '@/assets/img/svg/delete.svg'

export default {
  name: "index",
  data() {
    return {
      collectionsData: null,
      commentsData: null,
      userInfo: null,
      modal: false,
      modal_loading: false,
      defaultAvatar,
      manSvg,
      womanSvg,
      alterSvg,
      logoutSvg,
      trashSvg
    }
  },
  components: {
    NavBar, Copyright, imgUpload
  },
  methods: {
    routerToProductPage(id) {
      this.$router.push('/product-information?productId=' + id)
    },
    commit() {
      this.modal_loading = true;
      setTimeout(() => {
        this.modal_loading = false;
        this.api.userAdminUserInfoUpdate(this.userInfo).then(rs => {
          if (rs.data.data) {
            sessionStorage.setItem("website-user-info", JSON.stringify(this.userInfo));
            location.reload();
          }
        })
      }, 2000);
    },
    callBack(imgUrl) {
      this.userInfo.headPortrait = imgUrl
      this.api.userAdminUserInfoUpdate(this.userInfo).then(rs => {
        if (rs.data.data) {
          sessionStorage.setItem("website-user-info", JSON.stringify(this.userInfo));
          location.reload();
        }
      })
    },
    signOut() {
      this.$confirm('是否退出登录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.$store.commit('needLoading', true)
        setTimeout(() => {
          sessionStorage.clear();
          this.$router.push('/home')
          this.$store.commit('needLoading', false)
          this.$message({
            type: 'success',
            message: '退出登录成功!'
          });
        }, 1000)
      })
    },
    removeComment(comment) {
      if (this.userInfo.id === comment.userId) {
        this.$confirm('是否删除该评论?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.api.uCommentsRemove(comment.id).then(rs => {
            if (rs.data.data === 1) {
              this.$Message.success('删除成功');
              location.reload();
            } else {
              this.$Message.error('删除失败');
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消删除'
          });
        });
      }
    },
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem("website-user-info"))
    if (!sessionStorage.getItem("website-user-info")) {
      this.$router.push('/account')
    } else {
      this.api.uCollectionGetUserCollections(this.userInfo.id).then(rs => {
        this.collectionsData = rs.data.data
      })
      this.api.uCommentsGetUserComments(this.userInfo.id).then(rs => {
        this.commentsData = rs.data.data
      })
    }


  }
}
</script>

<style scoped>
#home_page_frame {
  width: 100%;
  height: 100%;
}


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
}

.footer-bar-frame {
  width: 100%;
  height: auto;
}


/deep/ .el-icon {
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 50px;
}

#home_page {
  width: 100%;
  background-color: rgba(255, 255, 255, .85);
  /*box-shadow: 0 0 5px rgba(122, 122, 122, .55);*/
}

#user_info {
  width: 78%;
  margin: auto;
  display: flex;
  padding: 2.5rem 0;
  box-sizing: content-box;
  border-bottom: 1px solid rgba(122, 122, 122, .38);
  position: relative;
}

#avatar_frame {
  width: 7.8rem;
  height: 7.8rem;
  margin-right: 3.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border-radius: 50%;
  box-sizing: content-box;
  transform: scale(1.2, 1.2);
  position: relative;
  top: 1rem;
  box-shadow: 0 0 5px rgba(122, 122, 122, .35);
}

#user_info_form {
  width: auto;
}

#alter_icon {
  width: 1.8rem;
  height: auto;
  position: absolute;
  top: 5px;
  right: 2.2rem;
  cursor: pointer;
}

#logout_icon {
  width: 1.8rem;
  height: auto;
  position: absolute;
  top: 5px;
  right: 0;
  cursor: pointer;
}

#user_info_ul {
  list-style: none;
  margin: 0;
  padding: 0;
  cursor: default;
}

.user-info-li {
  padding: 0 1.2rem 1.2rem .5rem;
  display: flex;
  /*justify-content: center;*/
  align-items: center;
}

.user-info-li > span {
  font-size: 1.1rem;
  color: rgba(22, 22, 22, .95);
}

.user-info-li > span > img {
  width: 1.5rem;
  height: 1.5rem;
  overflow: hidden;
  position: relative;
  top: 5px;
  left: 2px;
}

.model-radio {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 .5rem;
}

.user-content-view {
  width: 78%;
  margin: auto;
  padding: 1.5rem 0 2rem;
  border-bottom: 1px solid rgba(122, 122, 122, .38);
}

h2 {
  color: rgba(22, 22, 22, 0.95);
  font-weight: 400;
}


.content-item {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}


.content-ul {
  width: 100%;
  display: grid;
  justify-items: flex-start;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  list-style: none;
}

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
}

.item-intro-box {
  color: rgba(255, 201, 119, .85);
  width: 100%;
  padding: 2%;
  height: auto;
  font-weight: 400;
  background-color: rgba(22, 22, 22, .65);
  position: absolute;
  bottom: 0;
  right: 0;
  font-size: .85rem;
}

.item-score {
  color: rgba(255, 201, 119, .85);
  font-weight: 900;
  font-size: .85rem;
  text-align: right;
}

.item-introduction {
  padding-top: .2rem;
  color: rgba(220, 220, 220, .95);
  font-size: .5rem;
  font-weight: 100;
}


/deep/ .ivu-radio {
  padding-top: 3px;
}

/deep/ #woman_radio .ivu-radio-inner {
  border-color: #db639b;
}

/deep/ #woman_radio .ivu-radio-inner:nth-of-type(1):after {
  background-color: #db639b;
}

.model-icon {
  width: 1.2rem;
  height: 1.2rem;
  /*background-color: #008489;*/
  display: flex;
  justify-content: center;
  align-items: center;
}


@media screen and (max-width: 1024px) {
  .navbar-frame {
    height: 11.5%;
  }
}

@media screen and (max-width: 878px) {

  #search_bar {
    display: block;
  }
}

@media screen and (max-width: 600px) {
  .navbar-frame {
    height: 10.5%;
  }
}

@media screen and (max-width: 480px) {
  #home_page_frame {
    /*background-color: saddlebrown;*/
  }
}
</style>
<style scoped lang="scss">
.user-comments-view {
  width: 78%;
  margin: auto;
  padding: 1.5rem 0 2rem;

  .comments-title {
    width: 100%;
    display: flex;
    align-items: center;
    margin: 0;
    padding: 0;
    //justify-content: space-between;
    margin-bottom: 1rem;

    span {
      font-weight: 400;
      color: #7aba01;
      //margin-bottom: 1rem;
    }
  }

  .comments-wrap {
    width: 100%;
    margin-bottom: 2rem;
    //background-color: #7aba01;

    .comments-wrap-ul {
      width: 100%;
      list-style: none;

      .comments-wrap-li {
        width: 75%;
        display: flex;
        padding: 1rem 0;
        border-top: 1px solid rgba(66, 66, 66, .38);

        .avatar-box {
          margin: 0 .5rem 0 0;
          padding: .2rem 0;

          figure {
            width: 3.5rem;
            height: 3.5rem;
            overflow: hidden;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            box-shadow: 0 0 5px rgba(66, 66, 66, .38);

            img {
              width: 120%;
            }
          }
        }

        .comment-content {
          width: 100%;
          padding: .2rem 1rem;
          border-radius: 1rem;
          background-color: rgba(122, 186, 1, .05);

          .comments-desc {
            display: flex;
            width: 100%;
            height: 1.5rem;
            align-items: flex-end;
            //position: relative;


            .user-nickname {
              //font-weight: bold;
              //font-size: 1rem;
            }

            .comment-time {
              color: rgba(122, 122, 122, .85);
              margin: 0 1rem 0 .5rem;
              font-size: .85rem;
            }

            .del-trash {
              width: 1rem;
              position: relative;
              top: -3px;
              cursor: pointer;
            }
          }

          .the-comment {
            color: #5A6275;
          }
        }
      }

      .comments-wrap-li:nth-child(1) {
        border: none;
      }
    }
  }
}
</style>
