<template>
  <span id="update_drawer_pane">
    <Button style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改
     <Icon type="md-create"/>
    </Button>
  <Drawer
      :title="'书籍信息修改（编号：'+formData.id+'）'"
      v-model="drawerOpen"
      width="720"
      :mask-closable="false"
      :styles="styles"
  >
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="12">
            <img-upload :imgUrl="formData.coverUrl" @callBack="callBack">
              <template v-slot:icon-content>
                <i class="el-icon el-icon-reading"></i>
                <div class="el-upload__text">书籍封面上传</div>
              </template>
            </img-upload>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="书名" label-position="top">
              <Input v-model="formData.name" placeholder="书籍名称"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="子标题" label-position="top">
              <Input v-model="formData.title" placeholder="书籍子标题"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="标签" label-position="top">
              <Select v-model="formData.tages" placeholder="选择书籍标签">
                <Option value="文学">文学</Option>
                <Option value="随笔">随笔</Option>
                <Option value="历史">历史</Option>
                <Option value="小说">小说</Option>
                <Option value="科幻">科幻</Option>
                <Option value="奇幻">奇幻</Option>
                <Option value="悬疑">悬疑</Option>
                <Option value="推理">推理</Option>
                <Option value="哲学">哲学</Option>
                <Option value="工具">工具</Option>
                <Option value="科学技术">科学技术</Option>
                <Option value="专业知识">专业知识</Option>
                <Option value="其他">其他</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="作者" label-position="top">
              <Input v-model="formData.author" placeholder="书籍作者"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="ISBN号" label-position="top">
              <Input v-model="formData.isbn" placeholder="ISBN号"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="出版社" label-position="top">
              <Input v-model="formData.publisher" placeholder="书籍出版社"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="出版地区" label-position="top">
              <Input v-model="formData.country" placeholder="书籍出版地区"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="出版日期" label-position="top">
              <DatePicker v-model="formData.releaseDate" type="date" placeholder="书籍出版日期" style="display: block"
                          placement="bottom-end"></DatePicker>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="参考价格" label-position="top">
              <Input v-model="formData.price" placeholder="参考价格"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="价格来源" label-position="top">
              <Input v-model="formData.priceSource" placeholder="参考价格来源"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="页数" label-position="top">
              <Input v-model="formData.pages" type="number" placeholder="书籍页数"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="介绍" label-position="top">
              <Input v-model="formData.introduction" type="textarea" :autosize="{minRows: 3,maxRows: 3}"
                     placeholder="书籍简介"/>
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
        name: this.optionData.name,
        title: this.optionData.title,
        tages: this.optionData.tages,
        author: this.optionData.author,
        country: this.optionData.country,
        releaseDate: this.optionData.releaseDate,
        introduction: this.optionData.introduction,
        coverUrl: this.optionData.coverUrl,
        publisher: this.optionData.publisher,
        isbn: this.optionData.isbn,
        price: this.optionData.price,
        priceSource: this.optionData.priceSource,
        pages: this.optionData.pages
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
        this.api.dBookUpdate(this.formData).then(rs => {
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

/deep/ .el-icon{
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 50px;
}
</style>
