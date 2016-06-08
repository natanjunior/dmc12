package unb.controlador;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import unb.Fachada;

public class Backup {
	private Fachada fachada;
	
	public Backup(Fachada f) {
		this.fachada = f;
	}
	
	public void comprimir(String diretorio){
		try {
			comprimirTar(diretorio);
			comprimirGzip("../../diretorio.tar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void comprimirTar(String diretorio) throws IOException{
		FileOutputStream fOut = null;
        BufferedOutputStream bOut = null;
        TarArchiveOutputStream tOut = null;
 
        try {
            fOut = new FileOutputStream(new File("../../diretorio.tar"));
            bOut = new BufferedOutputStream(fOut);
            tOut = new TarArchiveOutputStream(bOut);
            addFileToTar(tOut, diretorio, "");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            tOut.finish();
            tOut.close();
            bOut.close();
            fOut.close();
        }
	}
	
	private static void addFileToTar(TarArchiveOutputStream tOut, String caminho, String base) throws IOException {
        File arq = new File(caminho);
        String entradaNome = base + arq.getName();
        TarArchiveEntry tarEntrada = new TarArchiveEntry(arq, entradaNome);
 
        tOut.putArchiveEntry(tarEntrada);
 
        if (arq.isFile()) {
            FileInputStream fInputStream = null;
            try {
                fInputStream = new FileInputStream(arq);
                IOUtils.copy(fInputStream, tOut);
                tOut.closeArchiveEntry();
            } finally {
                IOUtils.closeQuietly(fInputStream);
            }
 
        } else {
            tOut.closeArchiveEntry();
            File[] filhos = arq.listFiles();
 
            if (filhos != null) {
                for (File filho : filhos) {
                	addFileToTar(tOut, filho.getAbsolutePath(), tarEntrada + "/");
                }
            }
        }
    }
	
	private void comprimirGzip(String arquivo) throws IOException{
		try {
            FileInputStream fis = new FileInputStream(arquivo);
            FileOutputStream fos = new FileOutputStream(arquivo+".gz");
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
