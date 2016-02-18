package allen.com.rsstest.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Allen on 2015/5/26.
 */
public class MagnetFilePojo {

    private String fileName = null;
    private String fileSize = null;
    private String fileMagnet = null;
    private String fileUrl = null;

    public MagnetFilePojo() {
    }

    public MagnetFilePojo(String fileMagnet, String fileName, String fileSize, String fileUrl) {
        this.fileMagnet = fileMagnet;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
    }



    public String getFileMagnet() {
        return fileMagnet;
    }

    public void setFileMagnet(String fileMagnet) {
        this.fileMagnet = fileMagnet;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return fileMagnet+fileName+fileSize+fileUrl;
    }

}
