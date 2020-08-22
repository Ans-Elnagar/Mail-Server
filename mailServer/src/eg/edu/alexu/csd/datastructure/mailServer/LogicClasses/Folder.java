package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IFolder;
public class Folder implements IFolder {
	private String folder ;
	private String path;
	public Folder(String mail,String current) {
		setFolder(mail,current);
	}
	public void setFolder(String mail,String current) {
		folder=current;
		path = "Users/"+mail+folder;
	}
	public String getPath() {
		return path;
	}
	public String getFolder() {
		return folder;
	}
}
