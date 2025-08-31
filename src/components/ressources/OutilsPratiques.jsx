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

export default function OutilsPratiques() {
  const outils = [
    {
      name: "Prettier",
      type: "VSCode Extension",
      description: "A code formatter for consistent style.",
      img: "https://cdn-media-0.freecodecamp.org/size/w2000/2024/03/Prettier.png",
    },
    {
      name: "VSCode",
      type: "Logiciel",
      description: "Popular code editor from Microsoft.",
      img: "https://upload.wikimedia.org/wikipedia/commons/9/9a/Visual_Studio_Code_1.35_icon.svg",
    },
    {
      name: "WebStorm",
      type: "IDE",
      description: "Powerful IDE for JavaScript.",
      img: "https://resources.jetbrains.com/storage/products/webstorm/img/meta/webstorm_logo_300x300.png",
    },
    {
      name: "Ubuntu VM",
      type: "Machine Virtuelle Préconfigurée",
      description: "A preconfigured Linux environment.",
      img: "https://assets.ubuntu.com/v1/29985a98-ubuntu-logo32.png",
    },
  ];

  // removed per-page filtering tabs; keep data simple

  return (
    <div className="w-full max-w-4xl mx-auto mt-6 px-4">
      <h2 className="text-xl font-bold mb-2">Outils pratiques</h2>
      <p className="text-gray-500 text-sm mb-4">Sélectionnez et découvrez des outils utiles pour le développement.</p>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 mt-4">
        {outils.map((outil, i) => (
          <Card key={i}>
            <CardHeader>
              <CardTitle className="flex items-center gap-2"><AppWindowIcon className="w-4 h-4" /> {outil.name}</CardTitle>
              <CardDescription className="text-sm">{outil.type}</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="flex items-start gap-4">
                <img src={outil.img} alt={outil.name} className="h-12 w-12 rounded-md" />
                <p className="text-sm text-gray-700">{outil.description}</p>
              </div>
            </CardContent>
            <CardFooter>
              <Button size="sm">Details</Button>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}
