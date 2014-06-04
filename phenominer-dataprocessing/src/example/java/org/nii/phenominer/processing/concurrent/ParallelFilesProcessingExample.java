package org.nii.phenominer.processing.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelFilesProcessingExample {
	static List<Callable<String>> tasks = new ArrayList<Callable<String>>();

	public static void main(String[] args) throws Exception {
		File folder = new File("test");
		processFolder(folder);

		int threadCount = Runtime.getRuntime().availableProcessors() + 2;
		System.out.println(threadCount);
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);

		List<Future<String>> results = executor.invokeAll(tasks);
		System.out.println("===");
		for (Future<String> result : results)
			System.out.println(result.get());
		executor.shutdown();
	}

	static void processFolder(final File src) throws Exception {
		if (src.isDirectory()) {
			for (File file : src.listFiles())
				processFolder(file);
		} else {
			tasks.add(new Callable<String>() {
				public String call() throws Exception {
					System.out.println(src);
					return src.toString();
				}
			});
		}
	}
}
