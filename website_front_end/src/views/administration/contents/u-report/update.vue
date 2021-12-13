<template>
  <span id="update_drawer_pane">
    <Button :disabled="operatorUserRole !== 'root'" style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改
     <Icon type="md-create"/>
    </Button>
  <Drawer
      :title="'投诉信息修改（编号：'+formData.id+'）'"
      v-model="drawerOpen"
      width="720"
      :mask-closable="false"
      :styles="styles"
  >
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="用户编号" label-position="top">
              <Input disabled v-model="formData.userId" placeholder="用户编号"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="评论编号" label-position="top">
              <Input disabled v-model="formData.commentId" placeholder="评论编号"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="举报类型" label-position="top">
              <Input disabled v-model="formData.reportType" placeholder="举报类型"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="处理人" label-position="top">
              <Input v-model="formData.handler" placeholder="处理人"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="处理状态" label-position="top">
              <Select v-model="formData.state" placeholder="选择书处理状态">
                <Option value="0">未处理</Option>
                <Option value="1">已处理</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="回复信息" label-position="top">
              <Input v-model="formData.handlerReply" type="textarea" :autosize="{minRows: 3,maxRows: 5}"
                     placeholder="回复信息"/>
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

export default {
  name: "update",
  props: {
    operatorUserRole: {
      type: String,
      default() {
        return {}
      },
      required: true
    },
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
        userId: this.optionData.userId,
        commentId: this.optionData.commentId,
        reportType: this.optionData.reportType,
        handler: this.optionData.handler,
        handlerReply: this.optionData.handlerReply,
        state: this.optionData.state+''
      },
      notNull: false
    }
  },
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
        this.api.uReportUpdate(this.formData).then(rs => {
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
    callBack(imgUrl) {
      this.formData.coverUrl = imgUrl
    }
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
