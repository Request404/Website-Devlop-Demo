<template>
  <div id="add_drawer_pane">
    <Button @click="drawerOpen = true" type="success" style="margin-right: 8px">添加（ADD）</Button>
    <Drawer
        title="推荐记录信息添加"
        v-model="drawerOpen"
        width="720"
        :mask-closable="false"
        :styles="styles"
    >
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="24">
            <img-upload :imgUrl="formData.productCoverUrl" @callBack="callBack">
              <template v-slot:icon-content>
                <i class="el-icon el-icon-picture-outline"></i>
                <div class="el-upload__text">宣传封面上传</div>
              </template>
            </img-upload>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="文化产品编号" label-position="top">
              <Input v-model="formData.productId" placeholder="请输入要推荐的文化产品编号"/>
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
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      formData: {
        productId: null,
        productCoverUrl: null
      }
    }
  },
  components: {imgUpload},
  methods: {
    add() {
      console.log(this.formData);
      for (let key in this.formData) {
        if (this.formData[key] == null || this.formData[key] === '') {
          this.notNull = false
          break;
        } else {
          this.notNull = true
        }
      }
      if (this.notNull) {
        this.api.dRecordAdd(this.formData).then(rs => {
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
    },
    callBack(imgUrl){
      this.formData.productCoverUrl = imgUrl
    },
  }
}
</script>

<style scoped>
#add_drawer_pane {
  width: auto;
  height: auto;
}
/deep/ .el-icon{
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 50px;
}
</style>
