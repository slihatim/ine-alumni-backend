import { AppWindowIcon } from "lucide-react";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default function RscTextuelles() {
  const ebooks = [
    {
      title: "Clean Code",
      author: "Robert C. Martin",
      description:
        "A handbook of Agile Software Craftsmanship — focus on writing clean, maintainable code.",
    },
    {
      title: "The Pragmatic Programmer",
      author: "Andrew Hunt & David Thomas",
      description:
        "A practical guide to modern software development and career growth.",
    },
    {
      title: "Refactoring",
      author: "Martin Fowler",
      description:
        "Techniques for improving the design of existing code with safe transformations.",
    },
    {
      title: "You Don't Know JS (Yet)",
      author: "Kyle Simpson",
      description:
        "Deep dive into JavaScript's core mechanisms and best practices.",
    },
  ];

  return (
    <div className="w-full max-w-5xl mx-auto mt-6 px-4">
      <h2 className="text-xl font-bold mb-2">Ressources textuelles</h2>
      <p className="text-gray-500 text-sm mb-4">Ebooks et guides pour approfondir vos connaissances.</p>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 mt-4">
        {ebooks.map((ebook, i) => (
          <Card key={i}>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <AppWindowIcon className="w-4 h-4" /> {ebook.title}
              </CardTitle>
              <CardDescription className="text-sm">by {ebook.author}</CardDescription>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-gray-700">{ebook.description}</p>
            </CardContent>
            <CardFooter>
              <Button size="sm">Read</Button>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}