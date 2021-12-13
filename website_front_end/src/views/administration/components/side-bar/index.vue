<template>
  <div id="side_bar">
    <Row>
      <Col span="8">
        <Menu :active-name="activeName" :open-names="openNames" :theme="theme" :accordion="true" @on-select="goToPage"
              @on-open-change="openChange">
          <Submenu name="1">
            <template slot="title">
              <Icon type="md-person"/>
              我
            </template>
            <MenuItem name="/administration/home">
              <Icon type="md-person-add"/>
              个人信息
            </MenuItem>
          </Submenu>
          <Submenu name="2">
            <template slot="title">
              <Icon type="md-contacts"/>
              用户管理
            </template>
            <MenuItem v-if="operatorUserRole === 'root'" name="/administration/admin-user">
              <Icon type="md-contact"/>
              管理员用户
            </MenuItem>
            <MenuItem name="/administration/client-user">
              <Icon type="ios-contacts"/>
              客户端用户
            </MenuItem>
          </Submenu>
          <Submenu name="3">
            <template slot="title">
              <Icon type="md-cloud-upload"/>
              网站数据
            </template>
            <MenuItem name="/administration/d-movies">
              <Icon type="ios-easel"/>
              电影数据
            </MenuItem>
            <MenuItem name="/administration/d-books">
              <Icon type="md-book"/>
              书籍数据
            </MenuItem>
            <MenuItem name="/administration/d-musics">
              <Icon type="ios-musical-notes"/>
              音乐数据
            </MenuItem>
          </Submenu>
          <Submenu name="4">
            <template slot="title">
              <Icon type="ios-people"/>
              用户数据
            </template>
            <MenuItem name="/administration/u-comments">
              <Icon type="ios-chatboxes"/>
              用户评论
            </MenuItem>
            <MenuItem name="/administration/u-collections">
              <Icon type="md-star"/>
              用户收藏
            </MenuItem>
          </Submenu>
          <Submenu name="5">
            <template slot="title">
              <Icon type="md-filing"/>
              用户反馈
            </template>
            <MenuItem name="/administration/u-report">
              <Icon type="ios-megaphone-outline"/>
              评论举报
            </MenuItem>
            <MenuItem name="/administration/u-complaints">
              <Icon type="ios-megaphone"/>
              用户投诉
            </MenuItem>
          </Submenu>
          <Submenu name="6">
            <template slot="title">
              <Icon type="ios-stats"/>
              其他数据
            </template>
            <MenuItem name="/administration/u-score">
              <Icon type="md-happy"/>
              评分及访问情况
            </MenuItem>
            <MenuItem name="/administration/d-record">
              <Icon type="ios-cloud-circle"/>
              热门推荐
            </MenuItem>
          </Submenu>
        </Menu>
      </Col>
    </Row>
    <div id="bottom_container">
      <copyright/>
    </div>
  </div>
</template>

<script>
import Copyright from "./copyright";

export default {
  name: "index",
  components: {Copyright},
  data() {
    return {
      operatorUserRole: null,
      theme: 'dark',
      activeName: "/administration/home",
      openNames: ['1']
    }
  },
  methods: {
    goToPage(name) {
      this.$router.push(name)
      sessionStorage.setItem('activeName', name)
      // this.$store.commit('needLoading', true)
      // setTimeout(() => {
      //   this.$router.push(name)
      //   sessionStorage.setItem('activeName', name)
      //   this.$store.commit('needLoading', false)
      // }, 1500)
    },
    openChange(name) {
      sessionStorage.setItem('openNames', name)
    }
  },
  created() {
    this.operatorUserRole = JSON.parse(sessionStorage.getItem('website-user-info')).role
    if (sessionStorage.getItem('openNames')) {
      this.openNames = [sessionStorage.getItem('openNames')]
      this.activeName = sessionStorage.getItem('activeName')
    }
  }
}
</script>

<style scoped lang="scss">
#side_bar {
  width: auto;
  height: 100%;
  background-color: rgba(81, 90, 110, .95);
  position: relative;

  #bottom_container {
    width: 100%;
    height: auto;
    position: absolute;
    bottom: 0;
    background-color: rgba(81, 90, 110, .65);
    box-shadow: -2px 0 2px rgba(22, 22, 22.38);
  }
}

</style>
