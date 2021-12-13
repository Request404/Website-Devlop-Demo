<template>
  <div id="add_drawer_pane">
    <Button @click="drawerOpen = true" type="success" style="margin-right: 8px">添加（ADD）</Button>
    <Drawer
        title="管理员添加"
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
              <Input v-model="formData.username" placeholder="管理员用户名"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="密码" label-position="top">
              <Input v-model="formData.password" placeholder="管理员用户密码"/>
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
              <Select v-model="formData.role" placeholder="管理员身份">
                <Option value="root">root用户</Option>
                <Option value="admin">普通管理员</Option>
                <Option value="other">临时管理员</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="昵称" label-position="top">
              <Input v-model="formData.nickName" placeholder="为管理员取一个好听的昵称"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="邮箱" label-position="top">
              <Input v-model="formData.email" placeholder="管理员邮箱地址"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="联系方式" label-position="top">
              <Input v-model="formData.telephone" type="number" placeholder="管理员联系方式">
                <span slot="prepend">+ 86</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawerOpen = false">取消</Button>
        <Button type="primary" @click="add()">添加</Button>
      </div>
    </Drawer>
  </div>
</template>

<script>
import imgUpload from '../../../../components/img-upload'

export default {
  name: "add",
  data() {
    return {
      drawerOpen: false,
      ismail: false,
      notNull: true,
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      formData: {
        username: null,
        password: null,
        nickName: null,
        gender: null,
        telephone: null,
        email: null,
        headPortrait: null,
        role: null
      }
    }
  },
  components: {imgUpload},
  methods: {
    add() {
      let reg = /^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
      if (this.formData.username == null || this.formData.username === '') {
        this.notNull = false
      } else if (this.formData.password == null || this.formData.password === '') {
        this.notNull = false
      } else this.notNull = !(this.formData.role == null || this.formData.role === '');
      if (reg.test(this.formData.email)) {
        if (this.notNull) {
          this.api.userAddAdministration(this.formData).then(rs => {
            if (rs.data.data === 1) {
              this.$message({
                type: 'success',
                message: '添加成功!'
              });
              location.reload();
            } else if (rs.data.data === -1) {
              this.$message.error('该电影信息已存在，添加失败!');
            } else {
              this.$message.error('添加失败!');
            }
          }).catch(err => {
            this.$message.error('添加失败!');
            console.log(err);
          });
          this.drawerOpen = false
        } else {
          this.$message.warning('添加失败，数据信息不完善!');
        }
      } else {
        this.$message.warning('邮箱格式不正确!');
      }

    },
    callBack(imgUrl) {
      this.formData.headPortrait = imgUrl
    }
  }
}
</script>

<style scoped>
#add_drawer_pane {
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
