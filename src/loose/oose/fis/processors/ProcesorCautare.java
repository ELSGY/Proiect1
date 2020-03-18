package loose.oose.fis.processors;

import loose.oose.fis.documents.Document;

import java.util.ArrayList;

public class ProcesorCautare implements Procesor {
    private String text;
    private int prioritate;

    public ProcesorCautare(String text,int prioritate) {
        this.text = text;
	this.prioritate=prioritate;
    }


    public int proceseaza(ArrayList<Document> documente) {
        int res = 0;

        for (Document document : documente) {
            String[] continut = document.analizeaza();
            for (String cuvant : continut) {
                if (cuvant.equals(text)) {
                    res++;
                }
            }
        }

        return res;
    }

    int prioritate(){
	return prioritate;
   }

}
