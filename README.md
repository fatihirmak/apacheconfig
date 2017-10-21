# Apache HTTP Server Configuration Parser

## Model
Every entity in the configuration model is represented as an `Element`. An `Element` is the 
simplest model in the library that can return an `ElementWriter`.
Any `Element` that can contain multiple elements is called `ElementContainer`. Element containers
can return the list of the elements they container.

An Apache configuraion file is represented as `Document` which implements both `Element` and `ElementContainer`.
A document might have different kinds of elements:
- `Comment`: It is a comment element that can only have comment text.
- `Directive`: This is a directive object that can contain a single directive along with its arguments.
- `Section`: Sections are the scope elements that can have multiple directives/comments or other sections.

An example document:

These are comments
\#Apache httpd.conf file
\#This file is the main configuration holder for Apache server.

A directive with a single argument:
ThreadsPerChild 250

A directive with multiple arguments:
LoadModule imagemap_module modules/mod_imagemap.so

A section:
<FilesMatch "^.ht">
    Order allow,deny
    Deny from all
</FilesMatch>

## Reading

```
DocumentReader reader = new DocumentReader(new File("..."));
Document document = reader.getDocument();
while (Element element : document.getElements()) {
    System.out.println(element.getClass());
    if (element.getClass() == Directive.class) {
        Directive directive = (Directive) element;
        System.out.println(directive.getName());
        System.out.println(directive.getArguments());
    } else if (element.getClass() == Section.class) {
        Section section = (Section) element;
        System.out.println(section.getName());
        System.out.println(section.getElements());
    }
}
```

## Writing

```
Document document = new Document();
Directive directive = new Directive("ThreadsPerChild");
directive.getArguments().add("250");
document.getElements().add(directive);

Directive order = new Directive("Order");
order.getArguments().add("allow,deny");

Section section = new Section("FilesMatch");
section.getArguments().add("^.ht");
section.getElements().add(order);

document.add(section);

DocumentWriter writer = new DocumentWriter(new FileOutputStream("..."));
writer.write(document);

```