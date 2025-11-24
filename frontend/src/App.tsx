import { useState, useEffect } from 'react'
import './App.css'

interface Project {
  title: string;
  description: string;
  techStack: string[];
  link?: string;
  company?: string;
  role?: string;
  duration?: string;
}

interface Skill {
  id: number;
  name: string;
  category: string;
}

interface Profile {
  name: string;
  title: string;
  summary: string;
  projects: Project[];
  skills: Record<string, Skill[]>;
}

function App() {
  const [profile, setProfile] = useState<Profile | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const apiUrl = import.meta.env.VITE_API_URL || '';
    fetch(`${apiUrl}/api/profile`)
      .then(res => {
        if (res.ok) return res.json();
        // Fallback data for demo if backend not running
        return {
          name: "Karim Ibrahim",
          title: "Senior Backend Engineer",
          summary: "Senior Backend Engineer with 6 years of experience in Kotlin, Java, Spring Boot, and distributed systems. Passionate about building scalable, automated infrastructure with AWS, Terraform, and CI/CD systems.",
          projects: [
            {
              title: "ChargeBig - Mahle GMBH",
              company: "Brightskies",
              role: "Backend Engineer | DevOps & Cloud Engineer",
              duration: "Jan 2022 - Present",
              description: "Played a key role in evolving a highly available EV charging backend, supporting thousands of concurrent sessions under high traffic. Contributed to the redesign of an Axon Framework legacy system using DDD with Spring Boot (Kotlin). Built scalable, reusable AWS infrastructure with Terraform and Docker.",
              techStack: ["Kotlin", "Spring Boot", "MySQL", "Axon", "AWS", "Terraform", "Docker"],
              link: "https://chargebig.com"
            }
          ],
          skills: {
            "Languages": [{ id: 1, name: "Kotlin", category: "Languages" }, { id: 2, name: "Java", category: "Languages" }],
            "Cloud": [{ id: 3, name: "AWS", category: "Cloud" }]
          }
        }
      })
      .then(data => {
        setProfile(data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Failed to fetch profile", err);
        setLoading(false);
      });
  }, []);

  if (loading) return <div className="loading">Loading...</div>;
  if (!profile) return <div className="error">Failed to load profile.</div>;

  return (
    <div className="portfolio-container">
      <header className="hero">
        <div className="hero-content">
          <h1 className="name">{profile.name}</h1>
          <h2 className="title">{profile.title}</h2>
          <p className="summary">{profile.summary}</p>
          <div className="hero-actions">
            <a href="#contact" className="btn btn-primary">Get in Touch</a>
            <a href="#projects" className="btn btn-secondary">View Work</a>
          </div>
        </div>
      </header>

      <section className="skills-section">
        <h3>Technical Skills</h3>
        <div className="skills-grid">
          {Object.entries(profile.skills).map(([category, skills]) => (
            <div key={category} className="skill-category">
              <h4>{category}</h4>
              <div className="skill-list">
                {skills.map(skill => (
                  <span key={skill.id} className="skill-tag">{skill.name}</span>
                ))}
              </div>
            </div>
          ))}
        </div>
      </section>

      <section id="projects" className="projects-section">
        <h3>Key Projects</h3>
        <div className="projects-grid">
          {profile.projects.map((project, index) => (
            <div key={index} className="project-card">
              <div className="project-header">
                <h4>{project.title}</h4>
                {project.company && <span className="company">@ {project.company}</span>}
              </div>
              {project.role && <div className="role">{project.role}</div>}
              {project.duration && <div className="duration">{project.duration}</div>}
              <p className="description">{project.description}</p>
              <div className="tech-stack">
                {project.techStack.map(tech => (
                  <span key={tech} className="tech-tag">{tech}</span>
                ))}
              </div>
              {project.link && (
                <a href={project.link} target="_blank" rel="noopener noreferrer" className="project-link">
                  View Project <span className="arrow">→</span>
                </a>
              )}
            </div>
          ))}
        </div>
      </section>

      <section id="contact" className="contact-section">
        <h3>Let's Connect</h3>
        <p>Interested in working together? Reach out!</p>
        <div className="contact-links">
          <a href="mailto:Karim.ibrahemm@gmail.com" className="contact-link">Email Me</a>
          <a href="https://linkedin.com/in/karim-ibrahem" target="_blank" rel="noreferrer" className="contact-link">LinkedIn</a>
        </div>
      </section>

      <footer>
        <p>© {new Date().getFullYear()} {profile.name}. Built with Kotlin, Spring Boot & React.</p>
      </footer>
    </div>
  )
}

export default App
