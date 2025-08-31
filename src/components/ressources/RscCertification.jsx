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

export default function RscCertification() {
  const certs = [
    {
      name: "IBM AI Fundamentals Badge",
      field: "Artificial Intelligence (AI)",
      description:
        "Validates understanding of AI fundamentals and business use-cases.",
    },
    {
      name: "Google Cloud Data Engineer",
      field: "Cloud / Data",
      description:
        "Demonstrates ability to design and build data processing systems on GCP.",
    },
    {
      name: "AWS Certified Developer",
      field: "Cloud",
      description:
        "Shows competency in developing and maintaining applications on AWS.",
    },
    {
      name: "Coursera ML Specialization",
      field: "Machine Learning",
      description: "Covers core ML algorithms and practical implementation.",
    },
  ];

  return (
    <div className="w-full max-w-4xl mx-auto mt-6 px-4">
      <h2 className="text-xl font-bold mb-2">Ressources de certification</h2>
      <p className="text-gray-500 text-sm mb-4">
        Explorez des certifications et badges reconnus.
      </p>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 mt-4">
        {certs.map((c, i) => (
          <Card key={i}>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <AppWindowIcon className="w-4 h-4" /> {c.name}
              </CardTitle>
              <CardDescription className="text-sm">{c.field}</CardDescription>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-gray-700">{c.description}</p>
            </CardContent>
            <CardFooter>
              <Button size="sm">Learn</Button>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}
