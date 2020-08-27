package com.kyrutech.kylefyleimages;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;;

public class Main {

    private static List<ImageFile> imageFiles = new ArrayList<>();
    private static String originalDirectory;

    public static void main(String[] args) throws IOException {
        port(8080);
        if(args.length > 0) {
            if(Files.isDirectory(Paths.get(args[0]))) {
                originalDirectory = args[0];
//                loadImagesFile(args[0]);


                staticFiles.externalLocation(args[0]);
            }
        }


        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (args.length < 1) { //No directory passed in
                model.put("hasError", true);
                model.put("error", "No directory argument to display.");
            } else {
                if(Files.isDirectory(Paths.get(args[0]))) {
                    imageFiles = new ArrayList<>();
                    loadImagesFile(args[0]);
                }

                String currentDirectory = req.queryParams("directory");
                if(currentDirectory != null && !currentDirectory.isEmpty()) {
                    model.put("currentDirectory", currentDirectory);

                    List<ImageFile> images = imageFiles.stream()
                            .filter((i) -> i.getDirectory() != null && i.getDirectory().equals(currentDirectory))
                            .filter((i) -> i.getFileName() != null && !i.getFileName().isEmpty())
                            .sorted(Comparator.comparing(ImageFile::getFileName))
                            .collect(Collectors.toList());
                    model.put("images", images);

                } else { //For the root



                    List<ImageFile> images = imageFiles.stream()
                            .filter((i) -> i.getDirectory() == null || i.getDirectory().isEmpty())
                            .sorted(Comparator.comparing(ImageFile::getFileName))
                            .collect(Collectors.toList());
                    model.put("images", images);
                }

                List<ImageFile> directories = imageFiles.stream()
                        .filter((i) -> i.getFileName() == null || i.getFileName().isEmpty())
                        .collect(Collectors.toList());

                directories.forEach((d)->{
                    if (d.getDirectory().equals(currentDirectory)) {
                        d.setCurrent(true);
                    } else {
                        d.setCurrent(false);
                    }
                });

                model.put("directories", directories);

                model.put("directory", originalDirectory + File.separator);
            }
            return new MustacheTemplateEngine().render(
                    new ModelAndView(model, "index.html")
            );
        });
    }

    private static void loadImagesFile(String directory) throws IOException {
        Files.list(Paths.get(directory))
                .forEach((p) -> {
                    if (Files.isDirectory(p)) {

                        imageFiles.add(new ImageFile(p.toString().replace(originalDirectory + File.separator, ""), ""));
                        try {
                            loadImagesFile(p.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if(!p.getFileName().toString().startsWith(".")) { //We don't need to worry about hidden files
                            String subDirectory = p.getParent().toString().replace(originalDirectory, "");
                            if (subDirectory.startsWith(File.separator)) {
                                subDirectory = subDirectory.substring(1);
                            }
                            imageFiles.add(new ImageFile(subDirectory, p.getFileName().toString()));
                        }
                    }
                });
    }
}
