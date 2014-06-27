package org.nii.phenominer.nlp.util;

import java.util.concurrent.TimeUnit;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.StartedProcess;
import org.zeroturnaround.exec.listener.ProcessListener;

public class ZTExecExample {
	public static void main(String[] args) throws Exception {
		StartedProcess p = new ProcessExecutor().command("ping", "google.com").readOutput(true)
				.exitValues(0).addListener(new ProcessListener() {
				}).start();
		
		String out = p.future().get(30, TimeUnit.SECONDS).outputUTF8();
		System.out.println(out);
	}

}
