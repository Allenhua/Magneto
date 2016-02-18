package allen.com.rsstest.pojo;

/**
 * Created by Allen on 2016/2/17.
 */
public class FileDetailPojo {
    private String fileTile;
    private String fileSize;

    public FileDetailPojo() {
    }

    public FileDetailPojo(String fileSize, String fileTile) {
        this.fileSize = fileSize;
        this.fileTile = fileTile;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileTile() {
        return fileTile;
    }

    public void setFileTile(String fileTile) {
        this.fileTile = fileTile;
    }

    @Override
    public String toString() {
        return "FileDetailPojo{" +
                "fileSize='" + fileSize + '\'' +
                ", fileTile='" + fileTile + '\'' +
                '}';
    }
}
