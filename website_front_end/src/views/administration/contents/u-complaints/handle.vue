<template>
  <span id="update_drawer_pane">
<!--    <Button style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改-->
    <!--     <Icon type="md-create"/>-->
    <!--    </Button>-->
    <Button v-if="formData.state+'' ==='0' && this.operatorUserRole !== 'root'" type="success" size="small" style="margin-right: 5px"
            @click="drawerOpen = true">处理
    </Button>
    <span v-else-if="formData.state+'' ==='0'">未处理</span>
    <span v-else>已处理</span>
  <Drawer
      :title="'投诉信息处理（编号：'+formData.id+'）'"
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
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="投诉内容" label-position="top">
              <Input disabled v-model="formData.complaintContent" type="textarea" :autosize="{minRows: 3,maxRows: 5}"
                     placeholder="投诉内容"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="投诉回复" label-position="top">
              <Input v-model="formData.handlerReply" type="textarea" :autosize="{minRows: 5,maxRows: 8}"
                     placeholder="回复信息"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawerOpen = false">取消</Button>
        <Button type="primary" @click="handle()">修改</Button>
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
        complaintContent: this.optionData.complaintContent,
        handlerReply: this.optionData.handlerReply,
        state: this.optionData.state + ''
      },
      notNull: false
    }
  },
  methods: {
    handle() {
      if (this.formData.handlerReply) {
        this.formData.state = parseInt(this.formData.state)
        this.api.uComplaintsHandle(this.formData).then(rs => {
          if (rs.data.data) {
            this.$message({
              type: 'success',
              message: '处理成功!'
            });
            location.reload();
          } else {
            this.$message.error('处理失败!');
          }
        }).catch(err => {
          this.$message.error('处理失败!');
          console.log(err);
        });
        this.drawerOpen = false
      } else {
        this.$message.warning('回复信息不能为空!');
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
