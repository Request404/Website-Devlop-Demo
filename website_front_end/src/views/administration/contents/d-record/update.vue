<template>
  <span id="update_drawer_pane">
    <Button style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改
     <Icon type="md-create"/>
    </Button>
  <Drawer
      :title="'推荐记录信息修改（编号：'+formData.id+'）'"
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
        productId: this.optionData.productId,
        productCoverUrl: this.optionData.productCoverUrl
      },
      notNull: false
    }
  },
  components: {imgUpload},
  methods: {
    update() {
      for (let key in this.formData) {
        if (this.formData[key] == null || this.formData[key] === '') {
          this.notNull = false
          break;
        } else {
          this.notNull = true
        }
      }
      if (this.notNull) {
        this.api.dRecordUpdate(this.formData).then(rs => {
          if (rs.data.data) {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            location.reload();
          } else {
            this.$message.error('修改失败!');
          }
        }).catch(err => {
          this.$message.error('修改失败!');
          console.log(err);
        });
        this.drawerOpen = false
      } else {
        this.$message.warning('修改失败，数据信息不完善!');
      }
    },
    callBack(imgUrl){
      this.formData.productCoverUrl = imgUrl
    },
  }
}
</script>

<style scoped>
#update_drawer_pane {
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
