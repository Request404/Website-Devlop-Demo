<template>
  <span id="update_drawer_pane">
    <Button style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改
     <Icon type="md-create"/>
    </Button>
  <Drawer
      :title="'音乐信息修改（编号：'+formData.id+'）'"
      v-model="drawerOpen"
      width="720"
      :mask-closable="false"
      :styles="styles"
  >
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="8">
            <img-upload :imgUrl="formData.albumAvatarUrl" @callBack="callBack1">
              <template v-slot:icon-content>
                <i class="el-icon el-icon-mic"></i>
                <div class="el-upload__text">歌曲专辑封面上传</div>
              </template>
            </img-upload>
          </Col>
          <Col span="8">
            <img-upload :imgUrl="formData.singerAvatarUrl" @callBack="callBack2">
              <template v-slot:icon-content>
                <i class="el-icon el-icon-picture-outline"></i>
                <div class="el-upload__text">歌手封面上传</div>
              </template>
            </img-upload>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="歌曲名" label-position="top">
              <Input v-model="formData.name" placeholder="歌曲名称"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="专辑" label-position="top">
              <Input v-model="formData.album" placeholder="歌曲专辑"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="类型" label-position="top">
              <Select v-model="formData.type" placeholder="选择歌曲类型">
                <Option value="内地流行">内地流行</Option>
                <Option value="港澳台流行">港澳台流行</Option>
                <Option value="国际流行">国际流行</Option>
                <Option value="粤语">粤语</Option>
                <Option value="英语">英语</Option>
                <Option value="日语动漫">日语动漫</Option>
                <Option value="舞曲">舞曲</Option>
                <Option value="怀旧">怀旧</Option>
                <Option value="摇滚">摇滚</Option>
                <Option value="组合">组合</Option>
                <Option value="乡村">乡村</Option>
                <Option value="电音/重金属">电音/重金属</Option>
                <Option value="古典音乐">古典音乐</Option>
                <Option value="电影原声">电影原声</Option>
                <Option value="其他">其他</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="演唱者" label-position="top">
              <Input v-model="formData.singer" placeholder="歌曲演唱者"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="作词人" label-position="top">
              <Input v-model="formData.writeWords" placeholder="歌曲作词人"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="作曲人" label-position="top">
              <Input v-model="formData.compose" placeholder="歌曲作曲人"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="发行地区" label-position="top">
              <Input v-model="formData.country" placeholder="歌曲发行地区"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="发行年份" label-position="top">
              <Input v-model="formData.releaseYear" type="number" placeholder="歌曲发行年份"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="长度（秒）" label-position="top">
              <Input v-model="formData.minutes" type="number" placeholder="歌曲长度/秒"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="歌词" label-position="top">
              <Input v-model="formData.lyric" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="歌曲词"/>
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
        album: this.optionData.album,
        singer: this.optionData.singer,
        writeWords: this.optionData.writeWords,
        compose: this.optionData.compose,
        lyric: this.optionData.lyric,
        type: this.optionData.type,
        country: this.optionData.country,
        minutes: this.optionData.minutes,
        releaseYear: this.optionData.releaseYear,
        albumAvatarUrl: this.optionData.albumAvatarUrl,
        singerAvatarUrl: this.optionData.singerAvatarUrl
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
        this.api.dMusicUpdate(this.formData).then(rs => {
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
    callBack1(imgUrl){
      this.formData.albumAvatarUrl = imgUrl
    },
    callBack2(imgUrl){
      this.formData.singerAvatarUrl = imgUrl
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
