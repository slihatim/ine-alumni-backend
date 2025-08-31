import { CodeIcon } from "lucide-react";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default function RscInteractives() {
  const websites = [
    {
      name: "W3Schools",
      category: "Website",
      description: "Online web development tutorials, references and examples.",
    },
    {
      name: "FreeCodeCamp",
      category: "Tutorial",
      description: "Hands-on coding lessons and projects.",
    },
    {
      name: "CodePen",
      category: "Playground",
      description: "An online code editor and social development environment.",
    },
    {
      name: "LeetCode",
      category: "Practice",
      description: "Algorithmic challenges to practice problem solving.",
    },
  ];

  return (
    <div className="w-full max-w-4xl mx-auto mt-6 px-4">
      <h2 className="text-xl font-bold mb-2">Ressources interactives</h2>
      <p className="text-gray-500 text-sm mb-4">
        Sites et plateformes interactives pour s'entraîner et apprendre.
      </p>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 mt-4">
        {websites.map((site, i) => (
          <Card key={i}>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <CodeIcon className="w-4 h-4" /> {site.name}
              </CardTitle>
              <CardDescription className="text-sm">
                {site.category}
              </CardDescription>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-gray-700">{site.description}</p>
            </CardContent>
            <CardFooter>
              <Button size="sm">Open</Button>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}
