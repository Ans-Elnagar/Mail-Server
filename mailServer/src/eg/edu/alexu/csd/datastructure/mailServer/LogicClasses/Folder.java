package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IFolder;
public class Folder implements IFolder {
	private String folder ;
	private String path;
	public Folder(String current) {
		folder=current;
		setFolder(current);
	}
	public void setFolder(String current) {
		folder=current;
		path = "Users/"+current;
	}
	public String getPath() {
		return path;
	}
	public String getFolder() {
		return folder;
	}
}
