<template>
  <span id="update_drawer_pane">
    <Button style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改
     <Icon type="md-create"/>
    </Button>
  <Drawer
      :title="'用户信息修改（编号：'+formData.id+'）'"
      v-model="drawerOpen"
      width="720"
      :mask-closable="false"
      :styles="styles"
  >
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="12">
            <img-upload :imgUrl="formData.coverUrl" @callBack="callBack">
              <!--              <template v-slot:icon-content>-->
              <!--                <i class="el-icon el-icon-film"></i>-->
              <!--                <div class="el-upload__text">将电影封面拖到此处或<br>点击上传</div>-->
              <!--              </template>-->
            </img-upload>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="用户名" label-position="top">
              <Input disabled v-model="formData.username" placeholder="用户名"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="密码" label-position="top">
              <Input v-model="formData.password" placeholder="用户密码"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="6">
            <FormItem label="性别" label-position="top">
              <Select v-model="formData.gender" placeholder="选择性别">
                <Option value="男">男</Option>
                <Option value="女">女</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="身份" label-position="top">
              <Input disabled v-model="formData.role" placeholder="用户身份"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="昵称" label-position="top">
              <Input v-model="formData.nickName" placeholder="为用户取一个好听的昵称"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="邮箱" label-position="top">
              <Input v-model="formData.email" placeholder="用户邮箱地址"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="联系方式" label-position="top">
              <Input v-model="formData.telephone" type="number" placeholder="用户联系方式">
                <span slot="prepend">+ 86</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawerOpen = false">取消</Button>
        <Button type="primary" @click="update()">修改</Button>
      </div>
    </Drawer>
  </span>
</template>

<script>
import imgUpload from '../../../../components/img-upload'

export default {
  name: "update",
  props: {
    optionData: {
      type: Object,
      default() {
        return {}
      },
      required: true//要求必须赋值
    }
  },
  data() {
    return {
      drawerOpen: false,
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      formData: {
        id: this.optionData.id,
        username: this.optionData.username,
        password: null,
        nickName: this.optionData.nickName,
        gender: this.optionData.gender,
        telephone: this.optionData.telephone,
        email: this.optionData.email,
        headPortrait: this.optionData.headPortrait,
        role: this.optionData.role
      },
      notNull: false
    }
  },
  components: {imgUpload},
  methods: {
    update() {
      let reg = /^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
      if (reg.test(this.formData.email)) {
        this.api.userClientUserInfoUpdate(this.formData).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            location.reload();
          } else {
            this.$message.error(rs.data.msg);
          }
        }).catch(err => {
          this.$message.error('修改失败!');
          console.log(err);
        });
        this.drawerOpen = false
      } else {
        this.$message.warning('邮箱格式不正确!');
      }
    },
    callBack(imgUrl) {
      this.formData.coverUrl = imgUrl
    },
  }
}
</script>

<style scoped>
#update_drawer_pane {
  width: auto;
  height: auto;
}

/deep/ .el-icon {
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 50px;
}
</style>
