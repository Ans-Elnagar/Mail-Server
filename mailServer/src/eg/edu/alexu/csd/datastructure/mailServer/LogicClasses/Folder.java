package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IFolder;
public class Folder implements IFolder {
	private String path;
	public void setPath(String current) {
		path = "Users/"+App.userContact.getEmail()+current;
	}
	public String getPath() {
		return path;
	}
}
