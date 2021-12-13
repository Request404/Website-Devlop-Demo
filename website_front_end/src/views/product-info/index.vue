<template>
  <div v-if="this.productData" id="home_page_frame">
    <nav class="navbar-frame">
      <nav-bar>
        <template v-slot:search>
          <!--          <div>{{productData}}</div>-->
          <!--          <div>{{commentsData}}</div>-->
        </template>
      </nav-bar>
    </nav>
    <div class="home-page">
      <div class="product-content">
        <div class="product-introduction-content">
          <figure class="product-cover">
            <img v-if="productType === 3" referrerpolicy="never" :src="productData.albumAvatarUrl" alt="">
            <img v-else referrerpolicy="never" :src="productData.coverUrl" alt="">
          </figure>
          <div v-if="productType === 2" class="product-introduction-pane">
            <span><b>{{ $t('page.movie.name') }}:</b> {{ productData.name }}</span>
            <span><b>{{ $t('page.movie.type') }}:</b> {{ productData.type }}</span>
            <span><b>{{ $t('page.movie.country') }}:</b> {{ productData.country }}</span>
            <span><b>{{ $t('page.movie.releaseDate') }}:</b> {{ productData.releaseDate }}</span>
            <span><b>{{ $t('page.movie.director') }}:</b> {{ productData.director }}</span>
            <span><b>{{ $t('page.movie.writer') }}:</b> {{ productData.writer }}</span>
            <span><b>{{ $t('page.movie.actors') }}:</b> {{ productData.actors }}</span>
            <span><b>{{ $t('page.movie.minutes') }}:</b> {{ productData.minutes }}{{ $t('page.movie.min') }}</span>
            <span><b>{{ $t('page.movie.alternateName') }}:</b> {{ productData.alternateName }}</span>
          </div>
          <div v-else-if="productType === 1" class="product-introduction-pane">
            <span><b>{{ $t('page.book.name') }}:</b> {{ productData.name }}</span>
            <span><b>{{ $t('page.book.tages') }}:</b> {{ productData.tages }}</span>
            <span><b>{{ $t('page.book.author') }}:</b> {{ productData.author }}</span>
            <span><b>{{ $t('page.book.country') }}:</b> {{ productData.country }}</span>
            <span><b>{{ $t('page.book.releaseDate') }}:</b> {{ productData.releaseDate }}</span>
            <span><b>{{ $t('page.book.isbn') }}:</b> {{ productData.isbn }}</span>
            <span><b>{{ $t('page.book.price') }}:</b> {{ productData.price }}{{ $t('page.book.yuan') }}</span>
            <span><b>{{ $t('page.book.priceSource') }}:</b> {{ productData.priceSource }}</span>
            <span><b>{{ $t('page.book.pages') }}:</b> {{ productData.pages }}{{ $t('page.book.ye') }}</span>
            <span><b>{{ $t('page.book.title') }}:</b> {{ productData.title }}</span>
          </div>
          <div v-else class="product-introduction-pane">
            <span><b>{{ $t('page.music.name') }}:</b> {{ productData.name }}</span>
            <span><b>{{ $t('page.music.album') }}:</b> {{ productData.album }}</span>
            <span><b>{{ $t('page.music.type') }}:</b> {{ productData.type }}</span>
            <span><b>{{ $t('page.music.singer') }}:</b> {{ productData.singer }}</span>
            <span><b>{{ $t('page.music.writeWords') }}:</b> {{ productData.writeWords }}</span>
            <span><b>{{ $t('page.music.compose') }}:</b> {{ productData.compose }}</span>
            <span><b>{{ $t('page.music.country') }}:</b> {{ productData.country }}</span>
            <span><b>{{ $t('page.music.minutes') }}:</b> {{ productData.minutes }}</span>
            <span><b>{{ $t('page.music.releaseYear') }}:</b> {{ productData.releaseYear }}分钟</span>
          </div>
        </div>
        <div class="score-box">
          <p class="score-total">
            <span :class="productData.score.total === 0?'the-score-none':'the-score'">
              {{
                productData.score.total === 0 ? $t('page.score.noScore') : (productData.score.score / productData.score.total).toFixed(1)
              }}
            </span>
            <span class="the-total">
              {{ $t('page.score.total') }}: {{ productData.score.total }}
            </span>
            <el-button v-if="!this.isCollection" @click="collection" style="margin-left: .85rem" size="mini"
                       type="primary">
              {{ $t('page.collect') }}
            </el-button>
            <el-button v-else @click="removeCollection" style="margin-left: .85rem" size="mini" plain>
              {{ $t('page.collected') }}
            </el-button>
          </p>
          <p class="score-box-item">
            <span>{{ $t('page.score.fiveStar') }}：</span>
            <el-progress color="#F7BA2A" :stroke-width="8"
                         :percentage="parseFloat(fiveStarPercentage.toFixed(1))"></el-progress>
          </p>
          <p class="score-box-item">
            <span>{{ $t('page.score.fourStar') }}：</span>
            <el-progress color="#F7BA2A" :stroke-width="8"
                         :percentage="parseFloat(fourStarPercentage.toFixed(1))"></el-progress>
          </p>
          <p class="score-box-item">
            <span>{{ $t('page.score.threeStar') }}：</span>
            <el-progress color="#F7BA2A" :stroke-width="8"
                         :percentage="parseFloat(threeStarPercentage.toFixed(1))"></el-progress>
          </p>
          <p class="score-box-item">
            <span>{{ $t('page.score.twoStar') }}：</span>
            <el-progress color="#F7BA2A" :stroke-width="8"
                         :percentage="parseFloat(twoStarPercentage.toFixed(1))"></el-progress>
          </p>
          <p class="score-box-item">
            <span>{{ $t('page.score.oneStar') }}：</span>
            <el-progress color="#F7BA2A" :stroke-width="8"
                         :percentage="parseFloat(oneStarPercentage.toFixed(1))"></el-progress>
          </p>
        </div>
      </div>
      <div class="product-intro-pane">
        <h2 class="product-introduction-title">
          {{
            productType === 1 ? $t('page.book.introduction') : productType === 2 ? $t('page.movie.introduction') : $t('page.music.lyric')
          }}
        </h2>
        <div v-if="productType!==3" class="product-introduction-content"
             v-html="'<p style=text-indent:2em;margin-bottom:.25rem>'+
             productData.introduction.replace('\u3000\u3000','<p/><p style=text-indent:2em;margin-bottom:.25rem>')+
             '</p>'"></div>
        <div v-else class="product-introduction-content"
             v-html="'<p style=text-indent:2em;margin-bottom:.25rem>'+
             productData.lyric.replace('\n','<p/><p style=text-indent:2em;margin-bottom:.25rem>')+
             '</p>'"></div>
      </div>
      <div class="comment-frame">
        <h2 class="comments-title">
          <span style="margin-right: 2rem">{{ productData.name }}{{ $t('page.score.link') }}{{
              $t('page.score.comment')
            }}</span>
          <el-button :disabled="isCommented" @click="openCommentPage" type="success" size="small" icon="el-icon-edit"
                     plain>
            {{ isCommented ? $t('page.score.commented') : $t('page.score.writeComment') }}
          </el-button>
          <Modal
              :title="$t('page.score.comment')"
              v-model="commentPage"
              :styles="{top: '33%'}">
            <div class="comment-input-frame">
              <Input v-model="comment" type="textarea" :rows="4" :placeholder="$t('page.score.writeComment')"/>
            </div>
            <div class="score-block">
              <div class="score-position">
                <span style="margin-right: .5rem;position: relative; top: -1px; color: #606266" class="demonstration">
                  {{ $t('page.score.score') }}:
                </span>
                <el-rate v-model="score" :colors="colors"></el-rate>
                <span style="margin-left: .5rem;position: relative; top: -1px; color: #606266" class="demonstration">
                  {{ score + '.0' }}
                </span>
              </div>
            </div>
            <div slot="footer">
              <el-button type="success" @click="sendComment">{{ $t('page.score.submit') }}:</el-button>
            </div>
          </Modal>
        </h2>
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
                  <img v-if="userInfo?comment.userId === userInfo.id:false" @click="removeComment(comment)" class="del-trash"
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
      <footer class="footer-bar-frame">
        <copyright/>
      </footer>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/nav-bar';
import Copyright from '@/components/copyright';
import trashSvg from '@/assets/img/svg/delete.svg'

export default {
  name: "index",
  data() {
    return {
      trashSvg,
      productId: null,
      productType: null,
      productData: null,
      commentsData: null,
      userInfo: null,
      comment: '',
      score: 5,
      commentPage: false,
      isCommented: false,
      isCollection: false,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']
    }
  },
  computed: {
    oneStarPercentage() {
      if (this.productData.score.total) {
        return (this.productData.score.oneStar / this.productData.score.total) * 100
      } else {
        return 0
      }
    },
    twoStarPercentage() {
      if (this.productData.score.total) {
        return (this.productData.score.twoStar / this.productData.score.total) * 100
      } else {
        return 0
      }
    },
    threeStarPercentage() {
      if (this.productData.score.total) {
        return (this.productData.score.threeStar / this.productData.score.total) * 100
      } else {
        return 0
      }
    },
    fourStarPercentage() {
      if (this.productData.score.total) {
        return (this.productData.score.fourStar / this.productData.score.total) * 100
      } else {
        return 0
      }
    },
    fiveStarPercentage() {
      if (this.productData.score.total) {
        return (this.productData.score.fiveStar / this.productData.score.total) * 100
      } else {
        return 0
      }
    }
  },
  components: {
    NavBar, Copyright
  },
  methods: {
    openCommentPage(){
      if(this.userInfo){
        this.commentPage = true
      }else{
        this.$router.push('/account')
      }
    },
    sendComment() {
      if (this.userInfo) {
        if (this.comment.trim() !== '' && this.score !== 0) {
          this.$confirm('是否提交该评论?', '提示', {
            confirmButtonText: '提交',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let uComments = {
              userId: this.userInfo.id,
              productId: this.productData.id,
              userNickname: this.userInfo.nickName,
              avatarUrl: this.userInfo.headPortrait,
              score: this.score,
              comment: this.comment
            }
            this.api.uCommentsAdd(uComments).then(rs => {
              if (rs.data.data === 1) {
                this.$Message.success(rs.data.msg);
                this.commentPage = false
                location.reload();
              } else {
                this.$Message.error(rs.data.msg);
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消提交'
            });
          });
        } else {
          this.$Message.warning('数据缺失');
        }
      } else {
        this.$router.push('/account')
      }
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
    collection() {
      if (this.userInfo) {
        let uCollections = {
          userId: this.userInfo.id,
          productId: this.productData.id,
          productName: this.productData.name,
          productCoverUrl: this.productType !== 3 ? this.productData.coverUrl : this.productData.albumAvatarUrl
        }
        this.api.uCollectionAdd(uCollections).then(rs => {
          if (rs.data.data === 1) {
            this.$Message.success(rs.data.msg);
            location.reload();
          } else {
            this.$Message.error(rs.data.msg);
          }
        })
      } else {
        this.$router.push('/account')
      }
    },
    removeCollection() {
      this.$confirm('是否取消收藏?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let uComments = {
          userId: this.userInfo.id,
          productId: this.productData.id,
        }
        this.api.uCollectionRemove(uComments).then(rs => {
          if (rs.data.data === 1) {
            this.$Message.success(rs.data.msg);
            location.reload();
          } else {
            this.$Message.error(rs.data.msg);
          }
        })
      })
    }
  },
  created() {
    this.productId = this.$route.query.productId;
    this.productType = this.utils.getProductType(this.productId)
    if (this.productType === 1) {
      this.api.dBookGetInfo(this.productId).then(rs => {
        this.productData = rs.data.data[0]
      })
    } else if (this.productType === 2) {
      this.api.dMovieGetInfo(this.productId).then(rs => {
        this.productData = rs.data.data[0]
      })
    } else {
      this.api.dMusicGetInfo(this.productId).then(rs => {
        this.productData = rs.data.data[0]
      })
    }
    this.api.uCommentsGetProductionComments(this.productId, 0, 0).then(rs => {
      this.commentsData = rs.data.data
      if (this.userInfo) {
        for (const comment of this.commentsData) {
          if (comment.userId === this.userInfo.id) {
            this.isCommented = true;
            break;
          }
        }
      }
    })
    if (sessionStorage.getItem('website-user-info')) {
      this.userInfo = JSON.parse(sessionStorage.getItem('website-user-info'))
    }
    if (this.userInfo) {
      this.api.uCollectionGetUserCollections(this.userInfo.id).then(rs => {
        if (rs.data.data) {
          for (const collection of rs.data.data) {
            if (collection.productId === parseInt(this.productId)) {
              this.isCollection = true
              break;
            }
          }
        }
      })
    }

    this.api.uScoreVisitRecord(this.productId)
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

    .product-content {
      width: 78%;
      height: auto;
      margin: 0 auto;
      padding: 2rem 0;
      display: flex;
      justify-content: space-between;


      .product-introduction-content {
        display: flex;
        //justify-content: center;
        width: 50%;
        //background-color: #7aba01;

        .product-cover {
          width: auto;
          height: 18rem;
          margin-right: 1rem;
          box-shadow: 0 0 5px rgba(22, 22, 22, .38);

          img {
            width: auto;
            height: 100%;
          }
        }

        .product-introduction-pane {
          width: 15rem;

          span {
            display: block;
            margin: 0 0 .38rem;
          }
        }
      }

      .score-box {
        width: 16rem;
        margin-right: 4rem;
        height: auto;

        .score-total {
          margin-bottom: 1rem;
          display: flex;
          align-items: center;

          .the-score {
            font-size: 1.8rem;
            color: #F7BA2A;
            padding-right: .5rem;
          }

          .the-score-none {
            font-size: 1.5rem;
            color: #F7BA2A;
            padding-right: .5rem;
          }

          .the-total {
            font-size: .9rem;
            padding-top: .2rem;
            color: rgba(66, 66, 66, .95);
          }
        }

        .score-box-item {
          width: 90%;
          margin-bottom: .15rem;
          font-size: .85rem;
          color: #606266;
        }
      }
    }

    .product-intro-pane {
      width: 78%;
      margin: 1rem auto;

      .product-introduction-title {
        font-weight: 400;
        color: #7aba01;
        margin-bottom: 1rem;
      }

      .product-introduction-content {
        font-size: 1rem;
      }
    }

    .comment-frame {
      width: 78%;
      margin: 1rem auto;

      .comments-title {
        width: 70%;
        display: flex;
        align-items: center;
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
            padding: 1rem;
            border-top: 1px solid rgba(66, 66, 66, .38);

            .avatar-box {
              margin: 0 .5rem;
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

    .footer-bar-frame {
      width: 100%;
      height: auto;
    }
  }
}

//.comment-input-frame{
//  margin-bottom: 1rem;
//}

.score-block {
  width: 100%;
  padding: 1rem 0;
  position: relative;

  .score-position {
    position: absolute;
    right: 0;
    display: flex;
    justify-content: center;
    align-items: center;
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
